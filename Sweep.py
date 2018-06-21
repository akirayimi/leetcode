#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2018-06-21 11:42:13
# @Author  : Your Name (you@example.org)
# @Link    : link
# @Version : 1.0.0

import os
from PyQt5.QtCore import (Qt, QTimer, pyqtSignal)
from PyQt5 import QtCore, QtGui
from PyQt5.QtWidgets import (QWidget, QStatusBar, QLCDNumber, QSlider, QVBoxLayout,
                             QApplication, QPushButton, QGridLayout,
                             QHBoxLayout, QLabel, QMessageBox)
from PyQt5.QtGui import (QFont, QPainter, QColor)
from functools import reduce
import random

class MineExistException(Exception):
    def __init__(self):
        super().__init__()

class Cell(QLabel):
    def __init__(self, position, mine = False):
        super().__init__()
        self.position = position
        self.flag = False
        self.mine = mine

    def mousePressEvent(self, event):
        print(self.position)
        if event.button() == QtCore.Qt.LeftButton: # 左键扫
            if self.mine: # 触雷
                raise MineExistException()
            
        elif event.button() == QtCore.Qt.RightButton: # 右键标
            self.flag = True


class Board():
    def __init__(self, area, mineCount):
        self.line = area
        self.mineCount = mineCount

class Window(QWidget):
    mineTable = [] # 存地雷分布
    countTable = [] # 存周边地雷数量

    def __init__(self):
        super().__init__()
        self.initUI(5)

    def initUI(self, n):
        self.grid = QGridLayout()
        self.grid.setSpacing(1)
        self._buryMine(n, n)
        for position in self._positionList:
            label = Cell(position)
            label.setStyleSheet("background-color: #3e3e3e")
            self.grid.addWidget(label, *position)
        self.setLayout(self.grid)
        self.show()

    def _buryMine(self, width, height):
        self.mineTable = [[False for i in range (height)] for j in range(width)]
        self.countTable = [[0 for i in range (height)] for j in range(width)]
        total = int(len(self.mineTable) * len(self.mineTable[0]) / 3)
        for k in range(total):
            choice = random.choice([(i, j) for i in range (len(self.mineTable)) for j in range(len(self.mineTable[0]))])
            self.mineTable[choice[0]][choice[1]] = True
        self._fillCountTable()
        print(self.countTable)

    def _fillCountTable(self):
        for row, line in enumerate(self.mineTable):
            for col, cell in enumerate(line):
                self.countTable[row][col] = self._calc(row, col)

    def _calc(self, row, col):
        neighbours = [(i + row, j + col) for i in range(-1, 2) for j in range(-1, 2) if not(i == 0 and j == 0)]
        return sum(map(self._single, neighbours))


    def _single(self, position):
        if position[0] >= 0 and position[1] >= 0 and position[0] < len(self.mineTable) and position[1] < len(self.mineTable[0]):
            return 1 if self.mineTable[position[0]][position[1]] else 0
        return 0

if __name__ == '__main__':
    import sys
    app = QApplication(sys.argv)
    window = Window()
    window.setWindowTitle('Mine Sweep')
    sys.exit(app.exec_())
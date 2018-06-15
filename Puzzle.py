#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Date    : 2018-06-15 17:37:59
# @Author  : Your Name (you@example.org)
# @Link    : link
# @Version : 1.0.0

import sys

from PyQt5.QtGui import QPixmap
from PyQt5.QtWidgets import (QAction, QApplication, QDesktopWidget,
                             QGridLayout, QHBoxLayout, QLabel, QLCDNumber,
                             QMainWindow, QMessageBox, QPushButton, QToolTip,
                             QWidget, qApp)


class Line():
    line = list()
    def __init__(self, count):
        line = [0 for i in range(count)]

    def fullCheck(self):
        return reduce(lambda x, y: x & y, self.line)


class Puzzle(QWidget):

    __grid = list()

    def __init__(self, m = 12, n = 7):
        super().__init__()
        self.__grid = [([0] * n) for i in range(m)]
        self._initUI()

    def _initUI(self):
        pass


    def _afterDrop(self):
        self.__grid

    def _handleFullRow(self):
        buttomLine = self.__grid[-1:]



if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = Puzzle()
    ex.getInfo()
    sys.exit(app.exec_())

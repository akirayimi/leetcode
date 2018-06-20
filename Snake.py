from PyQt5.QtCore import (Qt, QTimer, pyqtSignal)
from PyQt5 import QtCore, QtGui
from PyQt5.QtWidgets import (QWidget, QStatusBar, QLCDNumber, QSlider, QVBoxLayout,
                             QApplication, QPushButton, QGridLayout,
                             QHBoxLayout, QLabel, QMessageBox)
from PyQt5.QtGui import (QFont, QPainter, QColor)
from enum import IntEnum
import time, random
# Color declaration
backgroundColor = "#2e3436"
buttonColor = "#ffffff"
textColor = "#000000"

greenColor = "#27bc10"
redColor = "#d81711"

class CrashException(Exception):
    def __init__(self, error = "hit wall!"):
        Exception.__init__(self, error)

class Direction(IntEnum):
    '''
    如果当前方向和目标方向之和为7，则表示想往相反的方向移动，不可以
    '''
    UP    = 1 # b(001)
    DOWN  = 6 # b(110)
    LEFT  = 2 # b(010)
    RIGHT = 5 # b(101)

class node():
    def __init__(self, _prev, position, _next):
        self._prev = _prev
        self._next = _next
        self.position = position

class body():
    _head = node(None, (0, 0), None)
    _tail = node(None, (0, 0), None)
    def __init__(self, headPosition, tailPosition):
        if headPosition[0] != tailPosition[0] and headPosition[1] != tailPosition[1]:
            raise Exception("the head and tail should in one line!")
        self._head = node(None, headPosition, None)
        self._tail = node(None, tailPosition, None)
        self._head._next = self._tail
        self._tail._prev = self._head
        current = self._head

        for i in range(headPosition[1] + 1, tailPosition[1]):
            b = node(None, (headPosition[0], i), None)
            current._next = b
            b._prev = current
            b._next = self._tail
            self._tail._prev = b
            current = b

    def addFromTail(self, position):
        b = node(self._tail, position, None)
        self._tail._next = b
        self._tail = b

    def addFromHead(self, position):
        b = node(None, position, None)
        b._next = self._head
        self._head._prev = b
        self._head = b

    def allPosition(self):
        ret = []
        cur = self._head
        while True:
            ret.append(cur.position)
            cur = cur._next
            if cur == None:
                break
        return ret

    def move(self, position):
        self.addFromHead(position)
        self._tail = self._tail._prev
        self._tail._next = None
    
class snk():
    _preColor = ""
    _bodyColor = buttonColor
    positionCache = []
    def __init__(self, board, length = 3):
        self._board = board
        self._preColor = board[0][0]
        self._currentDirection = Direction.LEFT
        height = len(board)
        width = len(board[0])
        self.body = body(((int)(height / 2), width - length), ((int)(height / 2), width - 1))

    def go(self):
        nextPositon = ()
        if self._currentDirection == Direction.LEFT:
            nextPositon = (self.body._head.position[0], self.body._head.position[1] - 1)
        elif self._currentDirection == Direction.RIGHT:
            nextPositon = (self.body._head.position[0], self.body._head.position[1] + 1)
        elif self._currentDirection == Direction.UP:
            nextPositon = (self.body._head.position[0] - 1, self.body._head.position[1])
        elif self._currentDirection == Direction.DOWN:
            nextPositon = (self.body._head.position[0] + 1, self.body._head.position[1])

        self._checkPosition(nextPositon)
        self.body.move(nextPositon)
        self.render()
        return nextPositon

    def eat(self, position):
        self.body.addFromHead(position)

    def _checkPosition(self, position):
        if position[0] < 0 or position[1] < 0 or position[0] >= len(self._board[0]) or position[1] >= len(self._board):
            raise CrashException()

    def render(self):
        
        #复原
        for position in self.positionCache:
            self._board[position[0]][position[1]] = self._preColor

        bodyPositions = self.body.allPosition()
        self.positionCache = bodyPositions

        for position in bodyPositions:
            self._board[position[0]][position[1]] = self._bodyColor

    def changeDirection(self, direction):
        if self._currentDirection + direction != 7:
            self._currentDirection = direction
        
class play(QtCore.QThread):  
  
    goSign = pyqtSignal()
  
    def __init__(self, speed = 5):  
        super().__init__()
        self.speed = speed 
  
    def run(self):  
        while True:
            self.goSign.emit()
            time.sleep(1 / self.speed)

    def destory(self):
        self.goSign.destory()


class Window(QWidget):
    _renderSingal = pyqtSignal()
    goal = 0
    def __init__(self, n):
        super().__init__()
        self.initUI(n)

    def initUI(self, n):
        self._positionList = [(i, j) for i in range(n) for j in range(n)]
        self._labels = [[0 for col in range(n)] for row in range(n)]
        self._colors = [[backgroundColor for col in range(n)] for row in range(n)]
        self.grid = QGridLayout()
        self.grid.setSpacing(1)
        self.setLayout(self.grid)
        for position in self._positionList:
            label = QLabel()
            self.grid.addWidget(label, *position)
            self._labels[position[0]][position[1]] = label
        # resetBtn = QPushButton("复位", self)
        # resetBtn.clicked.connect(self._reset)
        
        # startBtn = QPushButton("开始", self)
        # startBtn.clicked.connect(self._start)
        # self.grid.addWidget(startBtn)
        # self.grid.addWidget(resetBtn)
        self.snake = snk(self._colors)
        self.putFood()
        self._renderSingal.connect(self.render)
        self.setGeometry(300, 300, 400, 400)
        self.show()
        self._renderSingal.emit()
        self.playThread = play()
        self.playThread.goSign.connect(self.run)
        self.playThread.start()


    def putFood(self):
        foodPositions = list(set(self.snake.body.allPosition()) ^ set(self._positionList))
        self.currentfoodPosition = random.choice(foodPositions)
        self._colors[self.currentfoodPosition[0]][self.currentfoodPosition[1]] = redColor

    def run(self):
        try:
            nextPosition = self.snake.go()
            if nextPosition == self.currentfoodPosition:
                self.snake.eat(nextPosition)
                self.goal += 1
                self.putFood()
            self._renderSingal.emit()
        except CrashException as e:
            self.playThread.terminate()
            print(e)
            # self._deadMsg = QMessageBox(None, "Game Over", "本次得分: %d" % 5, QMessageBox.NoButton)
            # self._deadMsg.exec_()
            
    def render(self):
        for (label, color) in zip(self._labels, self._colors):
            for (pureLabel, pureColor) in zip(label, color):
                pureLabel.setStyleSheet("background-color: %s" % pureColor)
    
    def keyPressEvent(self, event):
        if event.key()  == QtCore.Qt.Key_Up :
            self.snake.changeDirection(Direction.UP)
        elif event.key() == QtCore.Qt.Key_Down : 
            self.snake.changeDirection(Direction.DOWN)
        elif event.key() == QtCore.Qt.Key_Left : 
            self.snake.changeDirection(Direction.LEFT)
        elif event.key() == QtCore.Qt.Key_Right : 
            self.snake.changeDirection(Direction.RIGHT)


if __name__ == '__main__':
    import sys
    app = QApplication(sys.argv)
    window = Window(30)
    sys.exit(app.exec_())
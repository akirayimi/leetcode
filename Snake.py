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

class Node():
    def __init__(self, _prev, position, _next):
        self._prev = _prev
        self._next = _next
        self.position = position

class Body():
    _head = Node(None, (0, 0), None)
    _tail = Node(None, (0, 0), None)
    def __init__(self, headPosition, tailPosition):
        if headPosition[0] != tailPosition[0] and headPosition[1] != tailPosition[1]:
            raise Exception("the head and tail should in one line!")
        self._head = Node(None, headPosition, None)
        self._tail = Node(None, tailPosition, None)
        self._head._next = self._tail
        self._tail._prev = self._head
        current = self._head

        for i in range(headPosition[1] + 1, tailPosition[1]):
            b = Node(None, (headPosition[0], i), None)
            current._next = b
            b._prev = current
            b._next = self._tail
            self._tail._prev = b
            current = b

    def addFromTail(self, position):
        b = Node(self._tail, position, None)
        self._tail._next = b
        self._tail = b

    def addFromHead(self, position):
        b = Node(None, position, None)
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
    
class Snk():
    _preColor = ""
    _bodyColor = buttonColor
    positionCache = []
    def __init__(self, board, length = 3):
        self._board = board
        self._preColor = board[0][0]
        self._currentDirection = Direction.LEFT
        height = len(board)
        width = len(board[0])
        self.body = Body(((int)(height / 2), width - length), ((int)(height / 2), width - 1))

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
        self.reFillColorBoard()
        return nextPositon

    def eat(self, position):
        self.body.addFromHead(position)

    def _checkPosition(self, position):
        if position[0] < 0 or position[1] < 0 or position[0] >= len(self._board[0]) or position[1] >= len(self._board):
            raise CrashException()

    def reFillColorBoard(self):
        
        #复原
        for position in self.positionCache:
            self._board[position[0]][position[1]] = self._preColor

        bodyPositions = self.body.allPosition()
        self.positionCache = bodyPositions

        for position in bodyPositions:
            self._board[position[0]][position[1]] = self._bodyColor

    def changeDirection(self, direction):
        if self._currentDirection | direction != 7:
            self._currentDirection = direction
        
class Play(QtCore.QThread):  
  
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
    goalSign = pyqtSignal()
    def __init__(self, n):
        super().__init__()
        self.initUI(n)

    def initUI(self, n):
        vbox = QVBoxLayout()
        goalBox = QHBoxLayout()
        btnBox = QHBoxLayout()
        self.grid = QGridLayout()
        self.grid.setSpacing(1)
        vbox.addLayout(goalBox)
        vbox.addLayout(self.grid)
        vbox.addLayout(btnBox)

        self.goalView = QLabel()
        self.goalView.setFixedHeight(40)
        goalBox.addWidget(self.goalView)

        self.startBtn = QPushButton("Start", self)
        self.resetBtn = QPushButton("Reset", self)
        self.startBtn.setFocusPolicy(QtCore.Qt.NoFocus) #设置按钮无法被聚焦。否则方向键会用来选择按钮而不是改变蛇的方向
        self.resetBtn.setFocusPolicy(QtCore.Qt.NoFocus)
        btnBox.addWidget(self.startBtn)
        btnBox.addWidget(self.resetBtn)

        self.setLayout(vbox)

        self._positionList = [(i, j) for i in range(n) for j in range(n)]
        self._labels = [[0 for col in range(n)] for row in range(n)]
        self._colors = [[backgroundColor for col in range(n)] for row in range(n)]
        for position in self._positionList:
            label = QLabel()
            self.grid.addWidget(label, *position)
            self._labels[position[0]][position[1]] = label
        self._renderSingal.connect(self.render)
        self.goalSign.connect(self.goalChange)
        self.resetUI()


    def resetUI(self):
        self.goal = 0
        self.snake = Snk(self._colors)
        self.putFood()
        self.setFixedSize(300, 400)
        self.show()
        self.goalSign.emit()
        self._renderSingal.emit()
        self.playThread = Play()
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
            self.goalSign.emit()
            self._renderSingal.emit()
        except CrashException as e:
            self.playThread.terminate()
            print(e)
            # self._deadMsg = QMessageBox(None, "Game Over", "本次得分: %d" % 5, QMessageBox.NoButton)
            # self._deadMsg.exec_()

    #得分信号的槽
    def goalChange(self):
        self.goalView.setText(str(self.goal))

    #根据colorBoard的颜色重绘面板
    def render(self):
        for (label, color) in zip(self._labels, self._colors):
            for (pureLabel, pureColor) in zip(label, color):
                pureLabel.setStyleSheet("background-color: %s" % pureColor)
    
    #覆盖父类的按键方法
    def keyPressEvent(self, event):
        if event.key()  == QtCore.Qt.Key_Up or event.key()  == QtCore.Qt.Key_W:
            self.snake.changeDirection(Direction.UP)
        elif event.key() == QtCore.Qt.Key_Down or event.key()  == QtCore.Qt.Key_S:
            self.snake.changeDirection(Direction.DOWN)
        elif event.key() == QtCore.Qt.Key_Left or event.key()  == QtCore.Qt.Key_A: 
            self.snake.changeDirection(Direction.LEFT)
        elif event.key() == QtCore.Qt.Key_Right or event.key()  == QtCore.Qt.Key_D: 
            self.snake.changeDirection(Direction.RIGHT)


if __name__ == '__main__':
    import sys
    app = QApplication(sys.argv)
    window = Window(10)
    window.setWindowTitle('Greedy Snake!')
    sys.exit(app.exec_())
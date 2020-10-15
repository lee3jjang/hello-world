from kivy.properties import StringProperty
from kivy.uix.button import Button
from kivymd.uix.list import (
    ThreeLineAvatarIconListItem, IRightBodyTouch
)
from kivymd.uix.behaviors import CircularRippleBehavior


class RightRoundedButton(IRightBodyTouch, CircularRippleBehavior, Button):
    pass

class Board(ThreeLineAvatarIconListItem):
    icon = StringProperty('android')
    comments = StringProperty()
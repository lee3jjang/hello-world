from kivy.lang import Builder
from datetime import datetime, date, time
from kivy.animation import Animation
from kivy.properties import StringProperty, NumericProperty
from kivy.core.window import Window
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.screenmanager import FadeTransition, NoTransition
from kivy.uix.button import Button
from kivymd.app import MDApp
from kivymd.uix.tab import MDTabsBase
from kivymd.uix.label import MDLabel
from kivymd.uix.button import (
    MDRaisedButton, MDFlatButton,
    MDRoundFlatButton, MDIconButton
)
from kivymd.uix.snackbar import Snackbar
from kivymd.uix.boxlayout import MDBoxLayout
from kivymd.font_definitions import theme_font_styles
from kivymd.uix.list import (
    OneLineAvatarIconListItem, ThreeLineAvatarIconListItem,
    ImageLeftWidget, ImageRightWidget, IRightBodyTouch
)
from kivymd.uix.selectioncontrol import MDCheckbox
from kivymd.uix.behaviors import CircularRippleBehavior
Window.size = (144*3, 256*3)

KV = '''
Screen:
    BoxLayout:
        orientation: 'vertical'
        MDToolbar:
            title: 'Daum Cafe'
            right_action_items: [['menu', lambda x: x]]

        ScrollView:
            MDList:
                id: board_list

<Board>
    on_release:
        print("Hello Board")

    IconLeftWidget:
        icon: root.icon

    RightRoundedButton:
        text: root.comments

        

<RightRoundedButton>
    color: 0, 0, 0, 1
    background_color: 0, 0, 0, 0
    canvas.before:
        Color:
            rgba: 1, 1, 1, 1
        RoundedRectangle:
            size: self.size
            pos: self.pos
            radius: [100]
    on_release: print("Hello RightRoundedButton")
'''

class RightRoundedButton(IRightBodyTouch, CircularRippleBehavior, Button):
    pass

class Board(ThreeLineAvatarIconListItem):
    icon = StringProperty('android')
    comments = StringProperty()


class Test(MDApp):
    def __init__(self, *args):
        super().__init__(*args)
        self.theme_cls.primary_palette = 'Red'
        self.theme_cls.theme_style = "Light"
        self.theme_cls.primary_hue = "300"

    def build(self):
        return Builder.load_string(KV)

    def on_start(self):
        board = Board(
            text="Three-line item with avatar",
            secondary_text="Author, 18:00, 7 Views",
            tertiary_text="Board",
            comments='11',
        )

        self.root.ids.board_list.add_widget(board)

Test().run()
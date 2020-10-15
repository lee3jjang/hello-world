from kivy.lang import Builder
from kivymd.app import MDApp
from kivymd.uix.snackbar import Snackbar
from datetime import datetime, date, time
from kivy.clock import Clock
from kivymd.uix.label import MDLabel
from kivy.animation import Animation
from kivy.properties import StringProperty, NumericProperty
from kivymd.uix.button import MDRaisedButton
from kivy.core.window import Window
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.boxlayout import BoxLayout
from kivymd.uix.tab import MDTabsBase
from kivymd.font_definitions import theme_font_styles
print(theme_font_styles)
Window.size = (144*3, 256*3)

KV = '''
BoxLayout:
    orientation: 'vertical'

    MDToolbar:
        title: 'Clock'
        right_action_items: [['dots-vertical', lambda x: x]]

    MDTabs:
        id: tabs

<StopwatchTab>
    orientation: 'vertical'

    Stopwatch:
        id: timer
        text: '{:02d}:{:02d}.{:02d}'.format(int(int(self.time)/60), int(self.time)%60, int(100*(self.time-int(self.time))))
        size_hint: None, None
        font_style: 'H3'
        size: 144*3, 700
        halign: 'center'
        valign: 'middle'

    StopwatchButton:
        
        text: 'Start'
        size_hint: None, None
        size: 144*3, 50
        on_release:
            self.start(*args)

        canvas:
            Color: 0, 0, 0
            Rectangle:
                size: self.size
'''

class StopwatchButton(MDRaisedButton):
    started = False
    def start(self, instance_button):
        if self.started == False:
            instance_button.parent.ids.timer.start()
            self.text = 'Stop'
            self.started = True
        else:
            Animation.stop_all(instance_button.parent.ids.timer)
            self.started = False


class StopwatchTab(FloatLayout, MDTabsBase):
    pass


class Stopwatch(MDLabel):
    time = NumericProperty()

    def start(self):
        self.anim = Animation(time=1e9, duration=1e9)
        # def callback(animation, widget):
        #     widget.text = 'Finished'
        # self.anim.bind(on_complete = callback)
        self.anim.start(self)


class Test(MDApp):
    def build(self):
        return Builder.load_string(KV)

    def on_start(self):
        self.root.ids.tabs.add_widget(StopwatchTab(text="Stopwatch"))
        self.root.ids.tabs.add_widget(MDTabsBase(text="Alarm"))
        self.root.ids.tabs.add_widget(MDTabsBase(text="Worldtime"))
        self.root.ids.tabs.add_widget(MDTabsBase(text="Timer"))

Test().run()
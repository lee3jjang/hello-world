from kivy.lang import Builder
from datetime import datetime, date, time
from kivy.animation import Animation
from kivy.properties import StringProperty, NumericProperty
from kivy.core.window import Window
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.screenmanager import FadeTransition, NoTransition
from kivymd.app import MDApp
from kivymd.uix.tab import MDTabsBase
from kivymd.uix.label import MDLabel
from kivymd.uix.button import MDRaisedButton, MDFlatButton
from kivymd.uix.snackbar import Snackbar
from kivymd.uix.boxlayout import MDBoxLayout
from kivymd.font_definitions import theme_font_styles
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

    BoxLayout:
        orientation: 'vertical'
        size_hint: None, None
        size: 144*3, 800
        Stopwatch:
            id: timer
            text: '{:02d}:{:02d}.{:02d}'.format(int(int(self.time)/60), int(self.time)%60, int(100*(self.time-int(self.time))))
            font_style: 'H2'
            halign: 'center'
            valign: 'middle'
      
    ScrollView:
        size_hint: None, None
        size: 144*3, 250
        pos_hint: {'center_x': .5, 'center_y': .35}
        
        MDList:
            orientation: 'vertical'
            id: records

    MDSeparator:
        height: "1dp"
        pos_hint: {'center_y': .1}

    ScreenManager:
        id: sm_buttons
        Screen:
            name: 'start_screen'
            StopwatchButton:
                text: 'Start'
                size_hint: 1, 0.1
                font_size: "18sp"
                pos_hint: {'center_x': 0.5}
                on_release:
                    self.start(*args)

        Screen:
            name: 'stop_screen'
            BoxLayout:
                orientation: 'horizontal'
                StopwatchButton:
                    text: 'Stop'
                    text_color: 1, 0, 0, 1
                    started: True
                    size_hint: 1, 0.1
                    font_size: "18sp"
                    pos_hint: {'center_x': 0.5}
                    on_release:
                        self.start(*args)

                StopwatchButton:
                    text: 'Record'
                    size_hint: 1, 0.1
                    font_size: "18sp"
                    pos_hint: {'center_x': 0.5}
                    on_release:
                        self.record(*args)

        Screen:
            name: 'continue_screen'
            BoxLayout:
                orientation: 'horizontal'
                StopwatchButton:
                    text: 'Continue'
                    started: False
                    size_hint: 1, 0.1
                    font_size: "18sp"
                    pos_hint: {'center_x': 0.5}
                    on_release:
                        self.start(*args)

                StopwatchButton:
                    text: 'Initialize'
                    size_hint: 1, 0.1
                    font_size: "18sp"
                    pos_hint: {'center_x': 0.5}
                    on_release:
                        self.initialize(*args)
        
'''

class StopwatchButton(MDFlatButton):
    started = False
    idx = 1
    def start(self, instance_button):
        if self.started == False:
            if instance_button.text == 'Continue':
                instance_button.parent.parent.parent.parent.ids.timer.start()
                instance_button.parent.parent.parent.parent.ids.sm_buttons.current = 'stop_screen'
            elif instance_button.text == 'Start':
                instance_button.parent.parent.parent.ids.timer.start()
                instance_button.parent.parent.parent.ids.sm_buttons.transition = NoTransition()
                instance_button.parent.parent.parent.ids.sm_buttons.current = 'stop_screen'
            else:
                raise Exception()
        else:
            Animation.stop_all(instance_button.parent.parent.parent.parent.ids.timer)
            instance_button.parent.parent.parent.parent.ids.sm_buttons.current = 'continue_screen'

    def initialize(self, instance_button):
        instance_button.parent.parent.parent.parent.ids.timer.time = 0
        instance_button.parent.parent.parent.parent.ids.timer.subtime = 0
        instance_button.parent.parent.parent.parent.ids.records.clear_widgets()
        instance_button.parent.parent.parent.parent.ids.sm_buttons.current = 'start_screen'

    def record(self, instance_button):
        records = instance_button.parent.parent.parent.parent.ids.records
        timer = instance_button.parent.parent.parent.parent.ids.timer
        idx = f'{self.idx:02d}'
        time = '{:02d}:{:02d}.{:02d}'.format(int(int(timer.time)/60), int(timer.time)%60, int(100*(timer.time-int(timer.time))))
        subtime = '{:02d}:{:02d}.{:02d}'.format(int(int(timer.time - timer.subtime)/60), int(timer.time - timer.subtime)%60, int(100*(timer.time - timer.subtime-int(timer.time - timer.subtime))))
        self.idx += 1
        timer.subtime = timer.time
        record = MDBoxLayout(height=40)
        record.add_widget(MDLabel(text=idx, halign='center'))
        record.add_widget(MDLabel(text=time, halign='center'))
        record.add_widget(MDLabel(text=subtime, halign='center'))
        records.add_widget(record)



class StopwatchTab(FloatLayout, MDTabsBase):
    pass


class Stopwatch(MDLabel):
    time = NumericProperty(0)
    subtime = NumericProperty(0)

    def start(self):
        self.anim = Animation(time=1e9, duration=1e9)
        self.anim.start(self)

class Test(MDApp):
    def __init__(self, *args):
        super().__init__(*args)
        self.theme_cls.primary_palette = 'Red'
        self.theme_cls.theme_style = "Light"
        self.theme_cls.primary_hue = "300"

    def build(self):
        return Builder.load_string(KV)

    def on_start(self):
        self.root.ids.tabs.add_widget(StopwatchTab(text="Stopwatch"))
        self.root.ids.tabs.add_widget(MDTabsBase(text="Alarm"))
        self.root.ids.tabs.add_widget(MDTabsBase(text="Worldtime"))
        self.root.ids.tabs.add_widget(MDTabsBase(text="Timer"))

Test().run()
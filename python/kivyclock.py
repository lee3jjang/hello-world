from kivy.clock import Clock
from kivy.lang import Builder
from kivymd.app import MDApp

KV = '''
MDIconButton:
    icon: "language-python"
    pos_hint: {"center_x": .5, "center_y": .5}
    on_release: app.start()
    user_font_size: "64sp"
'''

class Foo(MDApp):
    def start(self):
        Clock.schedule_interval(self.callback, 1)

    def build(self):
        return Builder.load_string(KV)

    def callback(self, dt):
        print(f"In callback: {dt}")

Foo().run()
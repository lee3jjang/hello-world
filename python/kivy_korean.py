import os
from pathlib import Path
from kivymd.app import MDApp
from kivy.lang import Builder

KV = '''
Screen:
    MDFlatButton:
        text: "버튼"
        font_size: "18sp"
'''

class MyApp(MDApp):

    def build(self):
        self.theme_cls.primary_palette = "Green"
        self.theme_cls.theme_style = "Dark"
        FONT_PATH = 'assets/fonts/'
        self.theme_cls.font_styles.update({
            "H1": [FONT_PATH + "NanumBarunGothic", 96, False, -1.5],
            "H2": [FONT_PATH + "NanumBarunGothic", 60, False, -0.5],
            "H3": [FONT_PATH + "NanumBarunGothic", 48, False, 0],
            "H4": [FONT_PATH + "NanumBarunGothic", 34, False, 0.25],
            "H5": [FONT_PATH + "NanumBarunGothic", 24, False, 0],
            "H6": [FONT_PATH + "NanumBarunGothic", 20, False, 0.15],
            "Subtitle1": [FONT_PATH + "NanumBarunGothic", 16, False, 0.15],
            "Subtitle2": [FONT_PATH + "NanumBarunGothic", 14, False, 0.1],
            "Body1": [FONT_PATH + "NanumBarunGothic", 16, False, 0.5],
            "Body2": [FONT_PATH + "NanumBarunGothic", 14, False, 0.25],
            "Button": [FONT_PATH + "NanumBarunGothic", 14, True, 1.25],
            "Caption": [FONT_PATH + "NanumBarunGothic", 12, False, 0.4],
            "Overline": [FONT_PATH + "NanumBarunGothic", 10, True, 1.5],
            "Money": [FONT_PATH + "NanumBarunGothic", 48, False, 0],
            "Button": [FONT_PATH + "NanumBarunGothic", 14, True, 1.25],
        })
        return Builder.load_string(KV)

MyApp().run()
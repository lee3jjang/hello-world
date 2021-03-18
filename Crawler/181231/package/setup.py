from setuptools import setup, find_packages

setup_requires = []

install_requires = [
    'django==2.2.18'
    ]

dependency_links = [
    'git+https://github.com/django/django.git@stable/1.6.x#egg=Django-1.6b4',
    ]

setup(
    name='my1stPkg',
    version='0.1',
    description='this is my 1st package',
    author='sangjin',
    author_email='lee3jjang@flowdas.com',
    packages=find_packages(),
    install_requires=install_requires,
    setup_requires=setup_requires,
    dependency_links=dependency_links,
    scripts=['./myPackage/bin/funniest-joke.bat'],
    entry_points={
        'console_scripts': [
            'publish = myPackage.manage:main',
            ],
        },
    )
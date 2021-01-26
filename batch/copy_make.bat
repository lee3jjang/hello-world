@echo off
set SPHINXBUILD=sphinx-build
set SOURCEDIR=sphinx_source
set BUILDDIR=docs

@REM sphinx-build -M html shpinx_source docs 
%SPHINXBUILD% -M html %SOURCEDIR% %BUILDDIR%

for /d %%I in (./*) do (
    echo %%I
)

for %%I in (./test/*.txt) do (
    echo %%I
)
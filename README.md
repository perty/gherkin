# gherkin
A small and not complete parser of Gherkin syntax that emits Html.

# Why?

I was wokring on a project usng Cucumber. I would like to try the readability of specifications
so I copied them to a Word document and printed. That was a bit of a fiddle.

I decided to take the Gherkin straight of and convert to HTML which Word can parse quite easy.

# What does it do?

It recurse through the directory given and looks for file with names ending in ".feature".
All files are parsed and output is on standard out. 

The effect is that all feature files are placed in one big HTML output which you can save 
and open with Word or a browser.

# What is it not?

It is not a report tool for parsing output from a execution of specification.

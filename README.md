# PuNIT
Welcome to the PuNIT software application GitHub repository!
PuNIT is a mock student assignment marking application developed by students at Macquarie University.

## Table of Contents
* [Description](#description)
* [Technology Used](#technology-used)
* [Application Features](#application-features)
* [Setup](#setup)
* [Room for Development](#room-for-development)
* [Project Status](#project-status)
* [Licenses](#licenses)

## Description
- This project is a command line interface (CLI) that runs locally on your machine
- The purpose of the project is to streamline the marking of COMP1000 student's Processing assignments
- It was developed as an aid to mark the assignments and give feedback on how the student performed
- Running the assignment with Processing and viewing the output would still be necessary to get an indicator of how the program visually performs
- PuNIT was modeled around a sample assignment provided by Ansgar Fenker, convenor for COMP4050 at Macquarie University

## Technology Used
- Java 16
- PMD code analysis tool (version 6.49.0)
- Processing (version 4.0.1)
- Apache POI (version 5.2.3)

## Application Features
- Static Code Analysis
- Compile Test
- Dynamic Testing

## Setup
User can run PuNIT using Windows, Mac, or Linux

For more information about the application please refer to the PuNIT user manual.

#### To install:
- Clone the repository or download the ZIP file

#### To run (Windows):
- Open a terminal on your computer
- In the terminal, navigate to the parent folder of PuNIT
- Execute the "run.bat" file if on Windows, and "run.sh" if on Linux/MacOS
- The first argument required is the parent folder location of the Processing files to be marked
- The second argument required is the location of the "TestFile.txt" file
- The third argument required is the location of the "staticRules.xml" file
- The output will be located in the "My Documents" folder on your computer

## Room for Development
Future development could be done by investigating if dynamic tests could be generalised to any assignment, rather than the current concrete implementation tied to MarchPenguin.

## Project Status
The project is set to finish in November 2022 with the end of COMP4050. There will be no new updates or bug fixes.

## Licenses
PuNIT is created under the MIT license. See the LICENSE file for details.

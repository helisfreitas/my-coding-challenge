# My Coding challenges - OwnWcTools

Challenge URL: https://codingchallenges.fyi/challenges/challenge-wc

## Description

OwnWcTool is a custom implementation inspired by the classic Unix wc tool. It allows users to count the number of lines, words, characters, and bytes in a file or multiple files. Additionally, it supports reading file paths from a NUL-separated list.

## Installation

To compile and use OwnWcTool, ensure you have the following:

- Java Development Kit (JDK) 21 or newer.

- Maven Build tools

### Compile the tool using your maven build tool
`mvn clean package`

### Run the tool using:

`java -jar target/ownwctool-<version>.jar [options] <file>`

Alternatively, use the script ownwctool.sh in Linux-Like System

`./owwctool.sh [options] <file>`

## Usage

### basic sintax

`ownwctool [options] <file>`

### Options

| Option |  Description |
| --- | ----------|
| -m, --chars | Count and display the number of characters in the file(s). |
| -l, --lines | Count and display the number of lines in the file(s). |
| -w, --words | Count and display the number of words in the file(s). |
| -c, --bytes | Count and display the number of bytes of file(s). |
| -x, --extracted-multiple-files | Read a file list from the file |
| -s, --stdin | Read a file list from standard input |
| -m, --chars | Count and display the number of characters in the file(s). |


### Examples

#### Count Lines, Words, and Bytes in a Single File

`./ownwctool.sh -lwc example.txt`

Output:

    100   500  2048 example.txt

#### Count Only Characters in a File

`./ownwctool.sh -m example.txt`

Output:

    600 example.txt

#### Count Lines, Words, bytes in the multiples files

`find . -name "*.txt" > list.txt`

`./ownwctool.sh -x list.txt`

output:

    9 9 99 ./list.txt
    2 6 73 ./file1.txt
    1 1 8 ./file2.txt


#### Count Lines, Words, bytes in the multiples files from stdin

*Note: place "-" in the final of the command*

`find . -name "*.txt" -print0 | ./ownwctool.sh -s -`

output:

    9 9 99 ./list.txt
    2 6 73 ./file1.txt
    1 1 8 ./file2.txt
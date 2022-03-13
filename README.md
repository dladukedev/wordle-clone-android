# wordle-clone-android
A Wordle Clone For Android

## Setup
- Add answer-bank.txt to the assets folder, this is the list of answers for the "Daily Challenge" mode
- Add word-bank.txt to the assets folder, this is the bank of valid words for guesses
  - This is also the bank of words "Practice" mode pulls from
  - Words from the daily answer bank must also be in this list to be considered valid guesses
- Both of these files should be a list of 5 letter lowercase words separated by a single return, example below
```
guess
words
banks
hello
```

## License
[MIT](https://github.com/dladukedev/wordle-clone-android/blob/master/LICENSE.md)

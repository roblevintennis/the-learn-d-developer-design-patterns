## Description

This is the home of _The Learn'd Developer: Design Patterns_, a book that examines Design Patterns using a test-driven approach. It contains the book and its code samples. **Design Patterns**, is the first in the The Learn'd Developer Series—a series of books aimed at developers that want to hone their craft.

## License

It is released under a Creative Commons Attribution-Noncommercial-No Derivative Works 3.0 United States License. 

## Viewing 

We regenerate the book often. See the files named:
the-learn-d-developer-design-patterns.xxx (where _xxx_ is the file
extension).

## Building

To generate the book in all formats simply type the following from the
project's root directory:

```bash
make all 
```

The Makefile itself is pretty descriptive and should get you going
quickly.

## Source code

The source code for the book is all in the ./resources directory. As the
code uses TDD you will find the following useful for running the tests:

### Java

For any Java source code, the unit tests were done using [JUnit4][junit]
in [Eclpise][eclipse]. Also, any test doubles use [Mockito][mockito].

### PHP

For any PHP source code, the unit tests were done using [phpunit][php_unit].

### JavaScript

For any JavaScript source code, the unit tests were done using [mocha.js][mocha]. Also, any test doubles use [sinon.js][sinon].


### Contribution guidelines

Contributions are welcome. Please see both the book's Preface and, of
course, any issues in the github issue tracker.

## Special acknowledgements

I feel obliged to commend Addy Osmani on his countless community contributions, particularly in developer education, and acknowledge that seeing the impact of his works inspired me to write myself. Also, the book: [Essential JS Design Patterns][addy] is where I shamelessly lifted the slightly tweaked pandoc build process being used here! 


[addy]: https://github.com/addyosmani
[sinon]: http://sinonjs.org/
[mocha]: http://visionmedia.github.com/mocha/
[eclipse]: http://www.eclipse.org/
[mockito]: http://code.google.com/p/mockito/
[junit]: https://github.com/KentBeck/junit
[php_unit]:https://github.com/sebastianbergmann/phpunit/


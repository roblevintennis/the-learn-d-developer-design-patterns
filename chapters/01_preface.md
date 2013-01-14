

# Preface

I decided to write this book because I thought it would be interesting to cover Design Patterns using a test-driven approach—I'm a sucker for "killing two birds with one stone". Because of this choice, the chapter on Observer pattern serves dual purposes with a particular focus on getting the reader up to speed on TDD basics. More advanced TDD topics like proper use of _test doubles_, writing testable code, etc., will not be addressed (although I try to provide links for further investigation where appropriate). All this said, the primary subject of this book is not TDD, it's Design Patterns! Also, please be forwarned that we will not be able to be particularly rigorous in our test coverage and will likely be omitting a lot of defensive code that would be required of a "real system". I have chosen understandibility over robustness for an obvious reason—I don't want the reader to get lost in details that don't really pertain to the point being made. This is a fairly typical approach, but I apologize in advanced if I somehow insult your sensibilities!

**Programming Languages**

Most of the examples will be in Java, PHP, and JavaScript, but some might use other languages. This should be fine since:

* TDD and Design Patterns should be language agnostic 
* The examples will be simple enough to follow (even if in an unfamiliar language)

**Resources**

I've generally listed any resources as clickable web links that you can learn more from, or, as links to where you might purchase the book that I'm referencing.

**Line breaks in code**

I've taken the liberty of purposely wrapping long lines that won't fit within the width of the page.

_Also, I've found that the generated code samples for PHP do not show up correctly unless I start and end the sample code blocks out with corresponding PHP opening and closing tags. So that's why every darn PHP code snippet has these!_

**Contributions**

I am definitely open to collaborative authorship (hey, I did put it on github!), provided that other authors follow the general style and spirit of the book. At some point, I'll try to define this all in a more concrete way. If you do want to contribute, you'll probably want to have a good look at the commented Makefile, and also notice the use of "extra lines" between code samples. I've managed to find workarounds for the somewhat finicky pandoc/docbook tool-chain (and I'm thankful that it works at all since these tools really make life so much easier!)

**Special acknowledgement**

I feel obliged to commend Addy Osmani on his countless community contributions, particularly in developer education, and acknowledge that seeing the impact of his work inspired me to write myself. Also, the book: [Essential JS Design Patterns][addy] is where I shamelessly lifted the pandoc build process being used here! 


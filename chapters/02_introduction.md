# Introduction

**Why should we study design patterns?**

As design patterns help us to use object-oriented best practices, it is useful to already have some meaningful experience in object oriented programming before attempting a deep study of design patterns. In fact, we have seen some try to dissuade a study of design patterns until the reader has reached an architect or designer skill level (whatever exactly that means). Obviously, the more prerequisite knowledge you have, the faster and you'll be able to grasp difficult abstract concepts; so perhaps there's some truth to the level of readiness that's ideal.

However, we would assert that you need not yet be an object oriented master to benefit from an initial study of design patterns. In fact, object oriented and good design go hand in hand—therefore, studying good design should reinforce your understanding of some object oriented concepts. Examples of design pattern implementations show object-oriented best practices "in action", and, since many of us learn best by example, the study of the two at the same time just might be complimentary.

Before reading this book, please take some of the following disclaimers and suggestions in to account:

* design patterns are an ongoing study; you'll likely learn new things when you return to the material at a later date
* therefore, assume that you'll need to return to the materials at later stages of your career
* if we don't explain something in a way that gets through to you, don't give up; try to look at a few similar examples on the web, or in other design pattern books, and look for the similarities—it'll start to make sense!
* Our goal is simply to "get your feet wet" with design patterns and not to cover them exhaustively. 

### TDD

As we have stated, TDD is _not_ the focus of this book, just an approach we've used to examine design patterns. However, unlike the majority of the book, the next chapter on Observer pattern **does** try to act as a sort of "TDD: up and running" tutorial—we painstakingly examine a TDD session (as we implement the Observer pattern) and go through each test case one by one in order to quickly bring you up to speed in TDD. 

_If you already have a lot of experience doing test-driven development, you may be "put off" by the verbosity of that chapter. Feel free to jump ahead, and, rest assured that subsequent chapters will be much more "to the point"._

### Design Patterns

We would be remiss not to mention the pioneers known as the _Gang of Four_ who, with their book [Design Patterns: Elements of Reusable Object-Oriented Software][design_patterns_book], described several reusable patterns that can be used to solve common recurring software design dilemmas.

We will discuss several, but not all, of the [GoF][gof] patterns, since they lay the framework from which new design patterns have evolved. As every pattern has its pros and cons, we will list the ones we've noticed as applicable. As we cover each particular pattern, we will not be adhering to any rigid _pattern structures_ like those presented in the following section. Our goal is to distill the material in an accessible way. Hence, we advise you to view this book as a supplementary material on the subject.

#### Pattern Structures

Formal design pattern descriptions are known to adhere to certain well-defined _pattern structures_. [Christopher Alexander][christopher_alexander] is an architecture academic who, while pioneering the whole design pattern concept in the first place, provided the [Alexandrian Form][alexandrian_form]. The _Gang of Four_ uses the [Portland Form][portland_form] (which includes no less than: _intent, motivation, applicability, structure, implementation, sample code, known uses, and related patterns sections_). 


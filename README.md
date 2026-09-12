# Impact Coding Test
- Author: Philip Loubser
- Email : philip.a.loubser@gmail.com

## Dependency list
- Maven version : 3.8.7
- Java version : 21

## Instructions
To run tests, run `mvn test` in the terminal while in the __number-range-summarizer__ directory.

## Program Description
The task was to implement an interface containing two methods, '__collect__' and '__summarizeCollection__'.
I describe the funciton, assumptions and implementation decisions of each briefly below.

### collect

This method takes in a string of comma-delimited integers, and returns a collection of
the integers. I have chosen the collection implementation to be an ArrayList for this
operation, as I think these are most commonly used. I have allowed negative numbers.
Any characters in the string that are not numbers, commas or a leading hyphen for
negating a number are ignored. In the case where a null value is passed in, it
will return an empty value instead of throwing an exception.

The list is created in a single pass over all the characters in the string.

### summarizeCollection

This method takes in a collection and prints out the integers in the collection,
grouping sequences when they appear. I have decided to keep the order of the
array as is, and I do not sort it to find more ranges. In addition to this, I have
decided that two consecutive number do not count as a range, and a range must
result in the exclusion of a number from the final string.  

The function uses an iterator to step over the elements in the collection, and not
using a for-each loop gives more control over the stepping. This function also
completes in linear time, with respect to the length of the input array.
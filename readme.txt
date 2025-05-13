DOCUMENT SEARCH ENGINE
A command-line search engine that maps words to document lists and allows users to search using logical operators like `AND` and `OR`.
It uses an array of linked lists to handle collisions through separate chaining.


INSTRUCTIONS
1. Place the name of your documents in the docFiles array. Make sure they are present in the same folder where Main.java is located. Label them as necessary using the docLabels array.
2. Run the program in the terminal or your IDE.
3. The program will prompt you to enter your query. Supported queries include
   - a single word search (Ex. "dog")
   - an OR word search (Ex. "dog OR cat OR rat
      - This will search for documents containing either word inputted by the user
      - For cases where one word is not found in any document and the other word is in at least one of the documents, a warning sign will be displayed, but will still search and display the documents containing the found word
   - an AND word search (Ex. "dog AND cat AND rat").
      - This will only search for documents containing both words inputted by the user
4. The program will search the documents for the specified word(s). If found, it will display the list of documents containing them. Otherwise, it will show an appropriate error or warning message.
5. After each search, the program will ask if you want to search again. Enter Y to continue or any other key to exit.


NOTES
- The search engine can only search for one word at a time. To search for multiple words, a logical operator must be used in the query.
- This program can handle searches with apostrophes(') and numbers.
- Only one type of logical operator (OR` or AND) may be used per search. A combination of both (Ex. "dog OR cat AND mouse") will return an error.
- The use of multiple operators are allowed as long as they are of the same type (Ex. "dog AND cat AND mouse").
- The logical operators must be uppercased when inputting in the search query.
- Queries are case-insensitive, meaning that "Dog", "dog", and "dOG" are treated the same when searched.
- Multiple white spaces between the word and the logical operator will be ignored (Ex. "dog        AND         cat").

AUTHORS
- De Guzman, Duncan Red Benedict
- Nabo, Frian Karl
- Tuquero, Jedric








# Document Search Engine
A command-line search engine that maps words to document lists and allows users to search using logical operators like `AND` and `OR`.
It uses an array of linked lists to handle collisions through separate chaining. 

## Instructions
1. Place the name of your documents in the `docFiles` array. Make sure they are present in the same folder where `Main.java` is located. Label them as necessary using the `docLabels` array.
2. Run the program in the terminal or your IDE.
3. The program will prompt you to enter your query. Supported queries include 
   - a single word search (Ex. `"dog"`)
   - an `OR` word search (Ex. `"dog OR cat OR rat"`)
   - an `AND` word search (Ex. `"dog AND cat AND rat"`).
4. The program will search the documents for the specified word(s). If found, it will display the list of documents containing them. Otherwise, it will show an appropriate error or warning message.
5. After each search, the program will ask if you want to search again. Enter `Y` to continue or any other key to exit.

## Notes
- Only one type of logical operator (`OR` or `AND`) may be used per search. A combination of both (Ex. `"dog OR cat AND mouse"`) will return an error. 
- Queries are **case-insensitive**, meaning that `Dog`, `dog`, and `dOg` are treated the same when searched.

### Authors
- [jed-ct](https://github.com/jed-ct)
- [Tzu-nami](https://github.com/Tzu-nami)
- [KiyanArc](https://github.com/KiyanArc)








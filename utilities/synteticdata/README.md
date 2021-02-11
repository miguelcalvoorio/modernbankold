# Data generators

For feeding documental (JSON based) databases, I have used a JSON generator to creat syntetic data

<https://next.json-generator.com/>

I haven't find yet the right tool for generate syntetic JSON data using ISO date formats. For that reason, using the templates I still need to do a small step to get final syntetic data:

- Replace ```"{\"$date\": \``` by ```{"$date":```
- Replace ```\"}"``` by ```"}```

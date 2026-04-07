**Пример. Построчное чтение из файла в разных кодировках**


**try**{

`   `// Создание объекта потока чтение байтов из файла

`   `FileInputStream fstream = **new**
**\
`       `FileInputStream("d:/1/file2.txt");

`   `// Создание объекта преобразующего байты в символы

`   `// второй параметр - кодировка символов

`   `InputStreamReader** inpstream = **new** 

`       `InputStreamReader(fstream,"windows-1251");

`   `// InputStreamReader** inpstream = **new** new 

`   `//       InputStreamReader(fstream,"UTF8")

`   `// Создание объекта буферного потока ввода

`   `//  с заданием размера буфера по умолчанию

`   `//  для ускорения процесса ввода

`   `BufferedReader br = **new** BufferedReader(inpstream);

`   `String strLine;

`   `//  Цикл построчного чтения файла

`   `**while** ((strLine = br.readLine()) != **null**){

`      `System.***out***.println(strLine);

`   `}

`   `br.close();

}**catch** (IOException e){

`   `System.***out***.println("Ошибка");

}

Используемые классы:

Класс **FileInputStream**  — поток чтение байтов из файла.

Класс InputStreamReader - поток чтения,** преобразующий байтовый поток в символьный в нужной кодировке.

Класс **BufferedReader** при считывании данных использует специальную область — буфер, куда «складывает»  прочитанные символы. В итоге, когда эти символы понадобятся в программе — они будут взяты из буфера, а не напрямую из источника данных (клавиатуры, файла и т.п.), а это экономит время.

Подробно про [кодировки](http://www.skipy.ru/technics/encodings.html#mandatory_enc) в Java 

**Пример. Запись в файл строк из коллекции**

String[] arr = { "one", "two", "three", "four","five" };

List<String> list = Arrays.*asList*(arr); 

**try** {

`   `File file = **new** File("d:/1/file2.txt ");

`   `// Создание объекта потока вывода байтов

`   `FileOutputStream fileOutputStream = 

`           `**new** FileOutputStream(file);

`   `// Создание объекта потока вывода символов в заданной кодировке

`   `OutputStreamWriter outputStreamWriter = **new** 

`      `OutputStreamWriter(fileOutputStream, "windows-1251");

`   `// Создание объекта буферного потока вывода

`   `// с заданием размера буфера по умолчанию

`   `// для ускорения процесса вывода

`   `BufferedWriter bw = **new** BufferedWriter(outputStreamWriter);

`   `**for** (String s : list) {

`       `bw.write(s + "\n");

`   `}

`   `bw.close(); // закрываем поток

} **catch** (Exception e) {

`   `e.printStackTrace();

}

Классы:

Класс FileOutputStream - байтового выходного поток

*Класс OutputStreamWriter*  - преобразование байтового выходного потока в символьный выходной поток в нужной кодировке

Класс  BufferedWriter - буферизация вывода. Все байты, записанные в BufferedWriter, сначала буферизуются во внутреннем байтовом массиве в BufferedWriter. Когда буфер заполнен, буфер сбрасывается в базовый OutputStreamWriterвсе сразу. Это ускоряет процесс вывода

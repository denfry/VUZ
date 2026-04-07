from pathlib import Path
from xml.sax.saxutils import escape
from zipfile import ZIP_DEFLATED, ZipFile

ROOT = Path(__file__).resolve().parent
OUTPUT_FILE = ROOT / "Отчет_ЛР1_Потоки_Variant11.docx"

TASK1_OUTPUT = """Введите начальный объем товара: 60
Введите максимальное число операций: 8
Покупатель-1 хочет купить 14 ед. товара. Куплено: 14. Остаток: 46. Операция 1 из 8
Покупатель-2 хочет купить 9 ед. товара. Куплено: 9. Остаток: 37. Операция 2 из 8
Покупатель-2 хочет купить 22 ед. товара. Куплено: 22. Остаток: 15. Операция 3 из 8
Покупатель-1 хочет купить 12 ед. товара. Куплено: 12. Остаток: 3. Операция 4 из 8
Покупатель-2 хочет купить 25 ед. товара. Куплено: 3. Остаток: 0. Операция 5 из 8
Товар полностью закончился.
Покупатель-2 завершил покупки.
Покупатель-1 завершил покупки.
Работа магазина завершена. Остаток товара: 0"""

TASK2_OUTPUT = """Производитель считал строку: Это первая тестовая строка файла.
Потребитель обработал строку: \"Это первая тестовая строка файла.\". Количество слов: 5
Производитель считал строку: В этой строке уже шесть отдельных слов.
Потребитель обработал строку: \"В этой строке уже шесть отдельных слов.\". Количество слов: 7
Производитель считал строку: Java потоки удобно использовать в учебных задачах.
Потребитель обработал строку: \"Java потоки удобно использовать в учебных задачах.\". Количество слов: 7
Производитель считал строку: Одна строка.
Производитель отправил признак конца файла.
Потребитель обработал строку: \"Одна строка.\". Количество слов: 2
Потребитель получил признак конца файла.
Обработка строк завершена."""


def paragraph(text: str, style: str = "Normal") -> str:
    return (
        f"<w:p><w:pPr><w:pStyle w:val=\"{style}\"/></w:pPr>"
        f"<w:r><w:t xml:space=\"preserve\">{escape(text)}</w:t></w:r></w:p>"
    )


def code_block(text: str) -> str:
    lines = text.splitlines() or [""]
    return "".join(paragraph(line, "Code") for line in lines)


def build_document_xml() -> str:
    task1_code = (ROOT / "Task1" / "Task1.java").read_text(encoding="utf-8")
    task2_code = (ROOT / "Task2" / "Task2.java").read_text(encoding="utf-8")
    task2_data = (ROOT / "Task2" / "data.txt").read_text(encoding="utf-8").strip()

    parts = [
        paragraph("Отчет по лабораторной работе №1", "Title"),
        paragraph("Тема: Использование потоков. Вариант 11", "Subtitle"),
        paragraph("Задание 1", "Heading1"),
        paragraph(
            "В магазин поступила заданная партия товара. Два потока производят покупку, "
            "объем товара определяется случайным числом. Если при покупке не хватает товара, "
            "то забирается весь остаток. Процесс завершается при исчерпании товара или после "
            "достижения заданного числа операций.",
            "Normal",
        ),
        paragraph("Код программы", "Heading2"),
        code_block(task1_code),
        paragraph("Результат выполнения", "Heading2"),
        paragraph("Пример запуска выполнен для объема товара 60 и максимума 8 операций.", "Normal"),
        code_block(TASK1_OUTPUT),
        paragraph("Задание 2", "Heading1"),
        paragraph(
            "Производитель считывает строки из файла и помещает их в блокирующую очередь. "
            "Потребитель извлекает строки из очереди и определяет количество слов в каждой строке.",
            "Normal",
        ),
        paragraph("Входные данные", "Heading2"),
        code_block(task2_data),
        paragraph("Код программы", "Heading2"),
        code_block(task2_code),
        paragraph("Результат выполнения", "Heading2"),
        code_block(TASK2_OUTPUT),
    ]

    body = "".join(parts) + (
        "<w:sectPr>"
        "<w:pgSz w:w=\"11906\" w:h=\"16838\"/>"
        "<w:pgMar w:top=\"1134\" w:right=\"850\" w:bottom=\"1134\" w:left=\"1134\" "
        "w:header=\"708\" w:footer=\"708\" w:gutter=\"0\"/>"
        "</w:sectPr>"
    )

    return (
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        "<w:document xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\" "
        "xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" "
        "xmlns:o=\"urn:schemas-microsoft-com:office:office\" "
        "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
        "xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" "
        "xmlns:v=\"urn:schemas-microsoft-com:vml\" "
        "xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\" "
        "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
        "xmlns:w10=\"urn:schemas-microsoft-com:office:word\" "
        "xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
        "xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" "
        "xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\" "
        "xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\" "
        "xmlns:wne=\"http://schemas.microsoft.com/office/2006/wordml\" "
        "xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\" "
        "mc:Ignorable=\"w14 wp14\">"
        f"<w:body>{body}</w:body></w:document>"
    )


def build_styles_xml() -> str:
    return '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:styles xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
  <w:docDefaults>
    <w:rPrDefault>
      <w:rPr>
        <w:rFonts w:ascii="Times New Roman" w:hAnsi="Times New Roman" w:cs="Times New Roman"/>
        <w:sz w:val="24"/>
        <w:szCs w:val="24"/>
      </w:rPr>
    </w:rPrDefault>
  </w:docDefaults>
  <w:style w:type="paragraph" w:default="1" w:styleId="Normal">
    <w:name w:val="Normal"/>
  </w:style>
  <w:style w:type="paragraph" w:styleId="Title">
    <w:name w:val="Title"/>
    <w:pPr><w:jc w:val="center"/><w:spacing w:after="200"/></w:pPr>
    <w:rPr><w:b/><w:sz w:val="32"/></w:rPr>
  </w:style>
  <w:style w:type="paragraph" w:styleId="Subtitle">
    <w:name w:val="Subtitle"/>
    <w:pPr><w:jc w:val="center"/><w:spacing w:after="300"/></w:pPr>
    <w:rPr><w:sz w:val="26"/></w:rPr>
  </w:style>
  <w:style w:type="paragraph" w:styleId="Heading1">
    <w:name w:val="heading 1"/>
    <w:pPr><w:spacing w:before="240" w:after="120"/></w:pPr>
    <w:rPr><w:b/><w:sz w:val="28"/></w:rPr>
  </w:style>
  <w:style w:type="paragraph" w:styleId="Heading2">
    <w:name w:val="heading 2"/>
    <w:pPr><w:spacing w:before="160" w:after="80"/></w:pPr>
    <w:rPr><w:b/><w:sz w:val="24"/></w:rPr>
  </w:style>
  <w:style w:type="paragraph" w:styleId="Code">
    <w:name w:val="Code"/>
    <w:pPr><w:spacing w:after="0"/></w:pPr>
    <w:rPr>
      <w:rFonts w:ascii="Consolas" w:hAnsi="Consolas" w:cs="Consolas"/>
      <w:sz w:val="18"/>
      <w:szCs w:val="18"/>
    </w:rPr>
  </w:style>
</w:styles>'''


def build_content_types_xml() -> str:
    return '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
  <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
  <Default Extension="xml" ContentType="application/xml"/>
  <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
  <Override PartName="/word/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml"/>
  <Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/>
  <Override PartName="/docProps/app.xml" ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml"/>
</Types>'''


def build_root_rels_xml() -> str:
    return '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
  <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
  <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
</Relationships>'''


def build_document_rels_xml() -> str:
    return '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
</Relationships>'''


def build_core_xml() -> str:
    return '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:dcterms="http://purl.org/dc/terms/"
  xmlns:dcmitype="http://purl.org/dc/dcmitype/"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <dc:title>Отчет по лабораторной работе №1</dc:title>
  <dc:creator>Codex</dc:creator>
  <cp:lastModifiedBy>Codex</cp:lastModifiedBy>
  <dcterms:created xsi:type="dcterms:W3CDTF">2026-03-25T00:00:00Z</dcterms:created>
  <dcterms:modified xsi:type="dcterms:W3CDTF">2026-03-25T00:00:00Z</dcterms:modified>
</cp:coreProperties>'''


def build_app_xml() -> str:
    return '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"
  xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
  <Application>Microsoft Office Word</Application>
</Properties>'''


def main() -> None:
    with ZipFile(OUTPUT_FILE, "w", compression=ZIP_DEFLATED) as docx:
        docx.writestr("[Content_Types].xml", build_content_types_xml())
        docx.writestr("_rels/.rels", build_root_rels_xml())
        docx.writestr("docProps/core.xml", build_core_xml())
        docx.writestr("docProps/app.xml", build_app_xml())
        docx.writestr("word/document.xml", build_document_xml())
        docx.writestr("word/styles.xml", build_styles_xml())
        docx.writestr("word/_rels/document.xml.rels", build_document_rels_xml())

    print(f"Отчет создан: {OUTPUT_FILE}")


if __name__ == "__main__":
    main()

from pathlib import Path
from xml.sax.saxutils import escape
from zipfile import ZIP_DEFLATED, ZipFile


ROOT = Path(__file__).resolve().parent
OUTPUT_FILE = ROOT / "Отчет_ЛР1_Потоки_Variant19.docx"


TASK1_OUTPUT = """Введите количество потоков: Автомобиль-1 пытается заправиться на 42 л. (Остаток на заправке: 1000 л)
Автомобиль-1 успешно заправился. Остаток бензина: 958 л. (Успешных заправок: 1/15)
Автомобиль-5 пытается заправиться на 4 л. (Остаток на заправке: 958 л)
Автомобиль-5 успешно заправился. Остаток бензина: 954 л. (Успешных заправок: 2/15)
Автомобиль-2 пытается заправиться на 33 л. (Остаток на заправке: 954 л)
Автомобиль-2 успешно заправился. Остаток бензина: 921 л. (Успешных заправок: 3/15)
Автомобиль-4 пытается заправиться на 49 л. (Остаток на заправке: 921 л)
Автомобиль-4 успешно заправился. Остаток бензина: 872 л. (Успешных заправок: 4/15)
Автомобиль-3 пытается заправиться на 8 л. (Остаток на заправке: 872 л)
Автомобиль-3 успешно заправился. Остаток бензина: 864 л. (Успешных заправок: 5/15)
Автомобиль-1 пытается заправиться на 25 л. (Остаток на заправке: 864 л)
Автомобиль-1 успешно заправился. Остаток бензина: 839 л. (Успешных заправок: 6/15)
Автомобиль-4 пытается заправиться на 43 л. (Остаток на заправке: 839 л)
Автомобиль-4 успешно заправился. Остаток бензина: 796 л. (Успешных заправок: 7/15)
Автомобиль-2 пытается заправиться на 4 л. (Остаток на заправке: 796 л)
Автомобиль-2 успешно заправился. Остаток бензина: 792 л. (Успешных заправок: 8/15)
Автомобиль-3 пытается заправиться на 23 л. (Остаток на заправке: 792 л)
Автомобиль-3 успешно заправился. Остаток бензина: 769 л. (Успешных заправок: 9/15)
Автомобиль-5 пытается заправиться на 44 л. (Остаток на заправке: 769 л)
Автомобиль-5 успешно заправился. Остаток бензина: 725 л. (Успешных заправок: 10/15)
Автомобиль-5 пытается заправиться на 36 л. (Остаток на заправке: 725 л)
Автомобиль-5 успешно заправился. Остаток бензина: 689 л. (Успешных заправок: 11/15)
Автомобиль-4 пытается заправиться на 26 л. (Остаток на заправке: 689 л)
Автомобиль-4 успешно заправился. Остаток бензина: 663 л. (Успешных заправок: 12/15)
Автомобиль-1 пытается заправиться на 41 л. (Остаток на заправке: 663 л)
Автомобиль-1 успешно заправился. Остаток бензина: 622 л. (Успешных заправок: 13/15)
Автомобиль-3 пытается заправиться на 43 л. (Остаток на заправке: 622 л)
Автомобиль-3 успешно заправился. Остаток бензина: 579 л. (Успешных заправок: 14/15)
Автомобиль-2 пытается заправиться на 32 л. (Остаток на заправке: 579 л)
Автомобиль-2 успешно заправился. Остаток бензина: 547 л. (Успешных заправок: 15/15)
Автомобиль-4 завершил работу.
Автомобиль-1 завершил работу.
Автомобиль-3 завершил работу.
Автомобиль-5 завершил работу.
Автомобиль-2 завершил работу.
Все заправки завершены. Процесс окончен."""

TASK2_OUTPUT = """Производитель выбрал элемент: -2
Потребитель: Пропущено число: -2 (не положительное).
Производитель выбрал элемент: 0
Потребитель: Пропущено число: 0 (не положительное).
Производитель выбрал элемент: 2
Производитель выбрал элемент: 2
Потребитель: Обработано положительное число: 2. Текущее произведение: 2
Производитель выбрал элемент: 2
Потребитель: Обработано положительное число: 2. Текущее произведение: 4
Производитель выбрал элемент: 5
Производитель выбрал элемент: -5
Потребитель: Обработано положительное число: 2. Текущее произведение: 8
Производитель выбрал элемент: 2
Потребитель: Обработано положительное число: 5. Текущее произведение: 40
Производитель выбрал элемент: -1
Производитель выбрал элемент: -2
Потребитель: Пропущено число: -5 (не положительное).
Производитель отправляет символ конца файла (EOF).
Потребитель: Обработано положительное число: 2. Текущее произведение: 80
Потребитель: Пропущено число: -1 (не положительное).
Потребитель: Пропущено число: -2 (не положительное).
Потребитель получил символ EOF. Остановка.
--- ИТОГ ---
Потребитель завершил работу. Итоговое произведение: 80
Программа завершена."""


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
        paragraph("Тема: Потоки. Вариант 19", "Subtitle"),
        paragraph("Задание 1", "Heading1"),
        paragraph(
            "Смоделировать работу автозаправочной станции в многопоточном режиме. "
            "Несколько потоков-автомобилей выполняют попытки заправки, обращаясь к общему ресурсу. "
            "Пользователь задает количество потоков перед запуском программы.",
            "Normal",
        ),
        paragraph("Код программы", "Heading2"),
        code_block(task1_code),
        paragraph("Результат выполнения", "Heading2"),
        paragraph("Пример запуска выполнен при количестве потоков = 5.", "Normal"),
        code_block(TASK1_OUTPUT),
        paragraph("Задание 2", "Heading1"),
        paragraph(
            "Реализовать схему производитель-потребитель с использованием блокирующей очереди. "
            "Производитель выбирает элементы из файла и помещает их в очередь, "
            "а потребитель обрабатывает только положительные числа и вычисляет их произведение.",
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
    return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
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
</w:styles>"""


def build_content_types_xml() -> str:
    return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
  <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
  <Default Extension="xml" ContentType="application/xml"/>
  <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
  <Override PartName="/word/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml"/>
  <Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/>
  <Override PartName="/docProps/app.xml" ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml"/>
</Types>"""


def build_root_rels_xml() -> str:
    return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
  <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/>
  <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/>
</Relationships>"""


def build_document_rels_xml() -> str:
    return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
  <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
</Relationships>"""


def build_core_xml() -> str:
    return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:dcterms="http://purl.org/dc/terms/"
  xmlns:dcmitype="http://purl.org/dc/dcmitype/"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <dc:title>Отчет по лабораторной работе №1</dc:title>
  <dc:creator>Codex</dc:creator>
  <cp:lastModifiedBy>Codex</cp:lastModifiedBy>
  <dcterms:created xsi:type="dcterms:W3CDTF">2026-03-11T00:00:00Z</dcterms:created>
  <dcterms:modified xsi:type="dcterms:W3CDTF">2026-03-11T00:00:00Z</dcterms:modified>
</cp:coreProperties>"""


def build_app_xml() -> str:
    return """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"
  xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
  <Application>Microsoft Office Word</Application>
</Properties>"""


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

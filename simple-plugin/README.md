Пример реализации плагина для "1C: Enterprise Development Tools".
Данный плагин добавляет возможность создания обработчика проведения документа через специальный диалог.

Перед началом работы необходимо отредактировать файл "target/default.target":
   - изменить путь до установленной версии "1C: Enterprise Development Tools" в строке "<location path="${dt_home}" type="Profile"/>"
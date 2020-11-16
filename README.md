## Клиентское приложение
Тестовое задание для кандидатов:
 
Разработать несколько проектов на Java:
1. Общую библиотеку с классом товара (Good)  полями code (Ineteger) и title (String).
2. Разработать клиентское приложение, которое читало бы информацию о товарах из XML файла и передавала бы ее по сети на сервер.
3. Разработать серверное приложение, которое принимало бы информацию о товарах от клиентского приложения и сохраняло бы их в какую-нибудь встраиваемую базу данных (например, в HSQL).
 
Клиент и сервер должны использовать общую библиотеку с классом товара, а также обеспечивать логирование, чтение настроек из файла, запуск с командной строки. Обойтись без спринга/серверов приложений и прочих тяжеловесных и реактивных решений.
 
Срок для задания - неделя.

## Образец .xml файла для загрузки
```
<?xml version="1.0" encoding="UTF-8" ?>
<goods>
<good code="1" title="Печенье" />
<good code="2" title="Огурцы" />
<good code="3" title="Крем" />
<good code="4" title="Марганцовка" />
</goods>
```
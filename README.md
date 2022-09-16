Данный репозиторий содержит в себе лабораторные работы по предмету Сервис-ориентированная архитектура (ИТМО).

# Лабораторная работа №1

  ## Задание
Разработать веб-сервис на базе сервлета, реализующий управление коллекцией объектов, и клиентское веб-приложение, предоставляющее интерфейс к разработанному веб-сервису. В коллекции необходимо хранить объекты класса Route, описание которого приведено ниже:

```
public class Route {
  private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
  private String name; //Поле не может быть null, Строка не может быть пустой
  private Coordinates coordinates; //Поле не может быть null
  private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
  private Location from; //Поле не может быть null
  private Location to; //Поле не может быть null
  private Long distance; //Поле может быть null, Значение поля должно быть больше 1
}
public class Coordinates {
  private Integer x; //Максимальное значение поля: 241, Поле не может быть null
  private double y; //Максимальное значение поля: 180
}
public class Location {
  private Integer x; //Поле не может быть null
  private int y;
  private Float z; //Поле не может быть null
}
```

Веб-сервис должен удовлетворять следующим требованиям:
- API, реализуемый сервисом, должен соответствовать рекомендациям подхода RESTful.
- Необходимо реализовать следующий базовый набор операций с объектами коллекции: добавление нового элемента, получение элемента по ИД, обновление элемента, удаление элемента, получение массива элементов.
- Операция, выполняемая над объектом коллекции, должна определяться методом HTTP-запроса.
- Операция получения массива элементов должна поддерживать возможность сортировки и фильтрации по любой комбинации полей класса, а также возможность постраничного вывода результатов выборки с указанием размера и порядкового номера выводимой страницы.
- Все параметры, необходимые для выполнения операции, должны передаваться в URL запроса.
- Данные коллекции, которыми управляет веб-сервис, должны храниться в реляционной базе данных.
- Информация об объектах коллекции должна передаваться в формате xml.
- В случае передачи сервису данных, нарушающих заданные на уровне класса ограничения целостности, сервис должен возвращать код ответа http, соответствующий произошедшей ошибке.
- Веб-сервис должен быть "упакован" в веб-приложение, которое необходимо развернуть на сервере приложений Payara.

Помимо базового набора, веб-сервис должен поддерживать следующие операции над объектами коллекции:
- Удалить один (любой) объект, значение поля distance которого эквивалентно заданному.
- Рассчитать сумму значений поля distance для всех объектов.
- Вернуть массив объектов, значение поля name которых содержит заданную подстроку.

Эти операции должны размещаться на отдельных URL.

Требования к клиентскому приложению:
- Клиентское приложение может быть написано на любом веб-фреймворке, который можно запустить на сервере helios
- Клиентское приложение должно обеспечить полный набор возможностей по управлению объектами коллекции, предоставляемых веб-сервисом -- включая сортировку, фильтрацию и постраничный вывод.
- Клиентское приложение должно преобразовывать передаваемые сервисом данные в человеко-читаемый вид -- параграф текста, таблицу и т.д.
- Клиентское приложение должно информировать пользователя об ошибках, возникающих на стороне сервиса, в частности, о том, что сервису были отправлены невалиданые данные.

Веб-сервис и клиентское приложение должны быть развёрнуты на сервере helios.

##  Комментарий
RoutesService - разработанный сервис

В процессе выполнения данной лабораторной работы я сосредоточилась на двух, наиболее интересных для меня, задачах: написание парсера и транслятора в SQL запросов к коллекции и реализация Discoverability. На данный момент доступны все, на мой взгляд, нужные операции в запросах, такие как арифметические операции, операции сравнения, логические операции and, not и or, а также проверка вхождения подстроки. Для удобного доступа к Discoverability был реализован UI. Считаю, что Discoverability помогает сделать RESTful API более полезным и элегантным.

Также для передачи информации об объектах коллекции была составлена xml-схема, специфицирующая формат передаваемых xml-документов. Однако для typescript’а не нашлось подходящей библиотеки для автоматической сериализации на основе xml-cхемы.

# Лабораторная работа №2

## Задание
Доработать веб-сервис и клиентское приложение из лабораторной работы #1 следующим образом:
- Отрефакторить сервис из лабораторной работы #1, переписав его на фреймворке Spring MVC REST с сохранением функциональности и API.
- Набор функций, реализуемых сервисом, изменяться не должен!
- Развернуть переработанный сервис на сервере приложений Jetty.
- Разработать новый сервис, вызывающий API существующего.
- Новый сервис должен быть разработан на базе Spring MVC REST и развёрнут на сервере приложений WildFly.
- Разработать клиентское приложение, позволяющее протестировать API нового сервиса.
- Доступ к обоим сервисам должен быть реализован с по протоколу https с самоподписанным сертификатом сервера. Доступ к сервисам посредством http без шифрования должен быть запрещён.
- Новый сервис должен располагаться на URL /navigator и реализовывать следующие операции:
  -  /route/{id-from}/{id-to}/{shortest} : найти самый короткий (или длинный) маршрут между указанными локациями
  -  /routes/{id-from}/{id-to}/{order-by} : найти все маршруты между указанными локациями, отсортировать список по заданному параметры

Оба веб-сервиса и клиентское приложение должны быть развёрнуты на сервере helios.

navigator – новый сервис
routes – переписанный старый сервис
routes-vue – фронт
Фронт раздаётся на https://se.ifmo.ru/~s243891/ 
Порты: 8586 8687

## Комментарий
В рамках данной лабораторной работы требовалось создать ещё один сервис и вызывать из него старый отрефакторенный. При этом оба сервиса должны работать по протоколу https. Трудностей с настройкой защищённого соединения не возникло. В задании указано, что доступ к сервисам должен осуществляться по протоколу https. Для этого достаточно серверного сертификата. Сгенерённый сертификат я использовала для обоих сервисов. Валидация самоподписанного сертификата выполнялась с помощью calback’а SSLFactory. (https://github.com/E1izabeth/RoutesService/blob/master/navigator/src/main/java/com/example/navigator/AppConfig.java).

Ошибки при вызове сервиса обрабатываются корректно. Если в процессе обработки вызова сервисом другого сервиса происходит ошибка, из неё получается информация об ошибке и генерируется другая ошибка с кодом 502 Bad Gateway с учетом этой информации, поэтому если произошла ошибка внутреннего сервиса, информация об этой ошибке не будет потеряна. (https://github.com/E1izabeth/RoutesService/blob/ff381a4131247ae6ec2e58c2e7d0901dcbb65ca0/navigator/src/main/java/com/example/navigator/NavigatorController.java#L48 )

Также пришлось реализовать дополнительно логику работы с локациями. Для них поддерживаются операции добавления(POST), удаления(DELETE), обновления(PUT) и получения (GET). Создание локаций происходит автоматически при создании или обновлении маршрута(route), если не найдено подходящей существующей локации в базе по условию ABS(:x*:x - x*x) < 1 AND ABS(:z*:z - z*z) < 1 AND ABS(:y*:y - y*y) < 1. Так как теперь маршрут ссылается на локацию, удалить мы её не можем. Поэтому при попытке удаления происходит поиск ссылок в базе данных на запрашиваемую локацию и, если ссылки есть, выбрасывается ошибка с кодом 409 Conflict.

# Лабораторная работа №3

## Задание
Переработать веб-сервисы из лабораторной работы #2 таким образом, чтобы они реализовывали основные концепции микросервисной архитектуры. Для этого внести в оба сервиса -- "вызываемый" (из лабораторной работы #1) и "вызывающий" (добавленный в лабораторной работе #2) перечисленные ниже изменения.

Изменения в "вызываемом" сервисе:
- Переработать сервис, обеспечив возможность его развёртывания на платформе Spring Cloud.
- Использовать все основные компоненты архитектуры Spring Cloud: Config Service, Load Balancer, Service Discovery.
- В качестве Service Discovery использовать Eureka.
- В качестве балансировщика нагрузки использовать Ribbon.
- Все запросы к сервисам должны проксироваться через API Gateway. В качестве API Gateway использовать Zuul Proxy.

Изменения в "вызывающем" сервисе:
- Сконфигурировать окружение для работы сервиса на платформе Spring Boot.
- Запустить второй экземпляр сервиса на другом порту. Реализовать балансировку нагрузки между экземплярами с помощью Haproxy.
- Оба веб-сервиса и клиентское приложение должны сохранить полную совместимость с API, реализованными в рамках предыдущих лабораторных работ.

## Комментарии

routes-vue – фронт  
RoutesService - сервис из 1 лабораторной работы на сервлетах  
navigator – вызывающий сервис (порт 8687,8689)  
routes – вызываемый сервис (порт 8586)  
config-server - ConfigServer (порт 50302)  
eureka-server - Eureka (порт 50100)  
zuul - Zuul + Ribbon (порт 50301)  
/home/s243891/ss3/config - локальный  репозиторий для централизованного хранения файлов конфигурации  
Фронт раздаётся на https://se.ifmo.ru/~s243891/  

```
Конфигурация haproxy (/home/s243891/ss3/haproxy.my.cfg):

defaults
  option forceclose
  option tcplog
  mode tcp

frontend http-front
  bind *:8690
  tcp-request inspect-delay 5s
  tcp-request content accept if { req_ssl_hello_type 1 }
  default_backend nav-svc

backend nav-svc
  balance static-rr
  option ssl-hello-chk
  #acl ok req_ssl_sni -i localhost
  #use-server nav1 if ok
  #use-server nav2 if ok
  server nav1 localhost:8687 check
  server nav2 localhost:8689 check
```

Так как оба сервиса в прошлой лабораторной работе уже были переписаны на Spring, в ходе этой лабораторной работы потребовалось лишь в вызываемый сервис (routes) добавить зависимости в pom.xml и написать конфигурацию.

После создания ConfigSerer’а в него были добавлены некоторые файлы конфигурации, которые можно посмотреть в config-repo.Далее для ServiceDiscovery была использована Eureka и настроено 5 реплик вызываемого сервиса routes с одной общей зоной. Интервал обновления 10 секунд. Далее была использована связка Zuul Proxy + Ribbon для проксирования и балансировки нагрузки.

В части работы второго сервиса, был использован haproxy для балансировки нагрузки.И реализован запуск двух экземпляров вызываемого сервиса. Также во фронте была изменена ссылка для работы через haproxy.

В ходе выполнения работы было не сразу очевидно, что Ribbon отвечает за определение вызываемого сервиса в составе Zuul, так как в большинстве гайдов он используется на вызывающей стороне. Сильно помогло сесть с бумажкой и карандашом, разобраться, как весь перечисленный зоопарк технологий может работать вместе. После того, как я нарисовала архитектуру, всё стало ясно.

Было интересно в ходе тестирования наблюдать за тем, как при выключении одной из двух работающих реплик, работоспособность приложения сохранялась.
В данной версии haproxy не вкомпилена поддержка ssl, поэтому пришлось запустить в режиме форвардинга tcp соединений. У этого есть преимущества, так как автоматически прокидывается ssl-сертификат с сервиса, его не нужно настраивать дополнительно на haproxy.


# Лабораторная работа №4

Переработать сервисы из лабораторной работы #3 следующим образом:
- Первый ("вызываемый") сервис переписать в соответствии с требованиями протокола SOAP.
- Развернуть переработанный сервис на сервере приложений по собственному выбору.
- Оставшийся сервис не модифицировать, не менять его API, протокол и используемый сервер приложений.
- Установить и сконфигурировать на сервере Helios программное обеспечение Mule ESB.
- Настроить интеграцию двух сервисов с использованием установленного программного обеспечения.
- Реализовать дополнительную REST-"прослойку", обеспечивающую возможность доступа к переработанному сервису клиентского приложения без необходимости его модификации. Никакой дополнительной логики, помимо вызовов SOAP-сервиса, разработанная REST-прослойка содержать не должна.


## Комментарий
В ходе выполнения работы для взаимодействия между сервисами было установлено ПО Mule ESB. В рамках созданного там проекта для интеграции двух сервисов был создан flow, показанный на рисунке ниже.

По ходу потока обработки запроса выполняется диспетчеризация для трёх случаев:
- обращение к endpoint`ам SOAP-сервиса
- обращение к WSDL-схемам сервиса
- остальные случаи

Для последнего случая формируется сообщение об ошибке, а для первых двух - запрос к реальному сервису. 

Если обрабатывается обращение к WSDL-схеме, содержащей информацию о расположении сервиса, обрабатывающего вызовы, передаваемая схема автоматически корректируется, чтобы указывать на Mule ESB, так как он выступает в роли маршрутизатора вызовов к службе.

navigator - вызывающий сервис, который не изменялся (порт 8687)
routes - вызываемый сервис, был переписан в соответствии с протоколом SOAP (порт 8787)
routesproxy - сервис-прослойка (порт 8383)
mule-routes-service - проект в Anypoint Studio (Mule ESB) (порт 9191)
Фронт раздаётся на https://se.ifmo.ru/~s243891/

Прокси-сервис, прослойка опирается на экспортируемое службой wsdl описание эндпоинтом endpoint`ов. Таким образом обеспечивается совместимость импортируемого интерфейса прокси-службы (потребителя) и экспортируемого интерфейса службы-обработчика. Mule ESB обеспечивает прозрачную маршрутизацию вызовов между этими службами, устраняя прямую зависимость от расположения последней (службы-обработчика).

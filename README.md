# MySimpleBook 個人記帳系統

- 專案目前已部署在 - [致富寶典](https://richnote.net)

> 起因：Spark first spring boot project in MacBook Pro，訓練自己時常記帳的習慣，順便磨練技術。

## 技術總覽

### 前端
1. JSP
2. HTML 5
3. javascript
4. CSS 3
5. [Bootstrap 5](https://getbootstrap.com)
6. [jQuery](https://jquery.com) 3.6.0
7. [jQuery-ui](https://jqueryui.com) 1.12.1
8. [jQuery-ui-datepicker](https://jqueryui.com/datepicker/)
9. [jQuery-cookie](https://plugins.jquery.com/cookie/) 1.4.1
10. [DataTables](https://datatables.net)(jQuery Version) 1.10.16
11. [Moment.js](https://momentjs.com)

### 後端
1. [JDK](https://www.oracle.com/tw/java/technologies/javase/javase-jdk8-downloads.html) 1.8.0_291
2. [Maven](https://maven.apache.org) 4.0.0
3. [Spring Boot](https://start.spring.io) v2.5.2
4. Spring v5.3.8
5. Spring AOP
6. [AWS SES](https://aws.amazon.com/tw/ses/)(中文)

### 資料庫
1. [MySQL](https://www.mysql.com) 8.0.23

### ORM
1. [MyBatis](https://mybatis.org/mybatis-3/zh/index.html)(中文) 3.5.7

### Log (日誌管理)
1. log4j
2. logback

### Server (伺服器)
1. [Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/) 9.0.50

## 功能介紹
### 訪客登入機制
1. 登入畫面設有「訪客登入」按鈕，可以以「匿名」的方式登入系統，試用裡面的全部功能。
2. 訪客登入後，系統會預建基礎資料，基礎資料包含：
    * 一個現金帳戶
    * 一個轉帳帳戶
    * 一個支出項目
    * 一個收入項目
3. 系統會在訪客初次登入、新增資料到達 5、10、25、50 筆資料時，提示訪客應該綁定帳號。
4. 綁定帳號有以下兩種方式：
    1. 帳號＆密碼
    2. Email認證碼
5. 透過Email認證碼綁定帳號的訪客，會先需要檢驗認證碼，系統才會寄發臨時密碼到信箱，讓訪客升級為使用者，並可以透過Email+臨時密碼進行登入。

### 帳戶管理
1. 使用者可以在帳戶管理進行新增、修改、刪除、停啟用、提高排序，並設定初始帳戶餘額。
2. 可設定信用卡的使用期限，預計將來系統會建立排程，超過使用期限的信用卡會自動停用。

### 帳戶類型管理
1. 系統預設4個選項，分別是：「現金」、「銀行」、「信用卡」、「儲值卡」。
2. 使用者可以對帳戶類別進行新增、修改、刪除、停啟用、提高排序。

### 支出功能
1. 使用者可以新增一筆支出，備註欄位為選填，最多可以輸入200個字。
2. 使用者可以查詢特定日期範圍的支出紀錄，特定日期如下：
    * 今天
    * 這個星期
    * 這個月
    * 前三個月
3. 使用者可以在支出紀錄的頁面，修改、刪除某一筆支出。

### 支出項目管理
1. 使用者可以在支出項目管理進行新增、修改、刪除、停啟用、提高排序。

### 收入功能
1. 使用者可以新增一筆收入，備註欄位為選填，最多可以輸入200個字。
2. 使用者可以查詢特定日期範圍的收入紀錄，特定日期如下：
    * 今天
    * 這個星期
    * 這個月
    * 前三個月
3. 使用者可以在收入紀錄的頁面，修改、刪除某一筆收入。

### 收入項目管理
1. 使用者可以在收入項目管理進行新增、修改、刪除、停啟用、提高排序。

### 轉帳功能
1. 使用者可以轉帳給其他帳戶，或是外部帳戶(外部帳戶是指不存在於系統內，或不是自己的帳戶)。
2. 轉帳功能可以輸入備註，最多可以輸入200個字。
3. 使用者可以查詢特定日期範圍的轉帳紀錄，特定日期如下：
    * 今天
    * 這個星期
    * 這個月
    * 前三個月

### 使用者基本資料
1. 使用者可以修改使用者名稱，使用者名稱會用於選單左上方。
2. 使用者可以修改密碼，系統會先比對舊密碼，再讓使用者修改新密碼。
3. 如果訪客是使用帳號綁定，升級為使用者之後，可以再綁定電子信箱(Email)，系統會寄發認證碼，檢驗認證碼之後，就可以使用帳號或Email進行登入。
4. 使用者可以刪除帳號，除了帳號會刪除之外，也會刪除的資料包括：帳戶、帳戶類別、支出、支出項目、收入、收入項目、轉帳。

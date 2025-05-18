## 實作需求
### 1. 幣別資料表 CRUD 等維護功能的 API。
      Controller : CurrencyController
      GET Method : localhost:8080/currency/{code}
      POST Method : localhost:8080/currency
      PUT Method : localhost:8080/currency/{id}
      DELETE Method : localhost:8080/currency/{id}
### 2. 呼叫 coindesk 的 API。
      Controller : CurrencyController
      GET Method : localhost:8080/currency/coindesk
### 3. 呼叫 coindesk 的 API,並進行資料轉換,組成新 API。
### 此新 API 包含以下內容:
#### A. 更新時間(時間格式範例:1990/01/01 00:00:00)。
#### B. 幣別相關資訊(幣別,幣別中文名稱,以及匯率)。  
      Controller : CurrencyController
      GET Method : localhost:8080/currency/newcoindesk

## 測試項目
### 1. 針對資料轉換相關邏輯作單元測試。
      Test class : CurrencyServiceTest
      Test method : testGetCoinDeskModifiedContent
### 2. 測試呼叫幣別對應表資料 CRUD API,並顯示其內容。
      Test class : CurrencyControllerTest
      Test method : testGetCurrency, testAddCurrency, testUpdateCurrency, testDeleteCurrency
### 3. 測試呼叫 coindesk API,並顯示其內容。
      Test class : CurrencyControllerTest
      Test method : testGetCoinDesk
### 4. 測試呼叫資料轉換的 API,並顯示其內容。
      Test class : CurrencyControllerTest
      Test method : testGetNewCoinDesk

## SQL Script
### Create Currency Table
    CREATE TABLE currency (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(5) UNIQUE NOT NULL,
    chinese_code VARCHAR(5) UNIQUE NOT NULL
    );

### Add Test Case
    INSERT INTO currency (code, chinese_code) VALUES
    ('USD', '美元'),
    ('JPY', '日圓'),
    ('EUR', '歐元'),
    ('NTD', '新台幣');

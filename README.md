<p align="center">
  <img src="https://github.com/user-attachments/assets/4d4eafbf-9908-4a72-a9aa-9e93a7e5eb09" alt="Logo CoinquyLife" width="200" />
</p>

# 📱 CoinquyLife – Android App

**CoinquyLife Android** è l’app mobile ufficiale per gestire la convivenza in modo semplice ed efficace.  
L’app si collega al backend **[CoinquyLife WebApp](https://github.com/minicla03/CoinquyLifeSE)** tramite API REST, sfruttando Retrofit per le chiamate remote.

---

## 🚀 Panoramica

Questa app ti permette di accedere a tutte le funzionalità di CoinquyLife in mobilità:

- 🧹 Visualizza e gestisci i turni di pulizia o altre attività.
- 💸 Controlla le spese condivise e il bilancio della casa.
- 🏆 Consulta la classifica gamificata per motivarti.
- 🏠 Entra o crea la tua casa condivisa.
- 📬 Ricevi notifiche e aggiornamenti sulla bacheca.
- 📊 Partecipa ai sondaggi (in arrivo).

---

## 🏗️ Architettura

L’app segue la **Clean Architecture** per garantire modularità, testabilità e manutenibilità.

### Livelli principali

- **Domain**: logica di business, use cases, entità.
- **Data**: repository, Retrofit service, datasource remoti.
- **Presentation**: ViewModel, UI (Activity/Fragment).

### Dipendenze chiave

- **Retrofit** per chiamate REST verso il backend remoto.
- **Java + Android SDK** come linguaggio e piattaforma.

---

## 🔌 Integrazione con Backend Remoto

- Database MongoDB è gestito dal backend web.
- Le chiamate HTTP verso i microservizi CoinquyLife sono effettuate tramite Retrofit.
- Autenticazione JWT gestita lato client per accesso sicuro.
- Ogni microservizio è raggiungibile tramite endpoint REST dedicati.

---

## 📁 Struttura del Progetto

``` 
com.coinquylife.android/
│
├── auth/ // Feature autenticazione
│ ├── presentation/
│ └── domain/
│
...
├── data/ // Implementazioni repository, Retrofit API, datasource remoti
``` 

# Prakash Jewellers

> *Ek Naam, Vishwas Ka* — Built on trust since 1999.

A full-stack website for Prakash Jewellers, inspired by leading jewellery brands.

- **Backend:** Spring Boot 3.5 (Java 21) + Spring Data JPA + H2 (file-based DB)
- **Frontend:** React 18 + Vite
- **Live rates:** Gold & silver spot prices are fetched live from a free, key-less
  provider ([gold-api.com](https://api.gold-api.com)) and converted to INR using a
  live USD→INR rate ([open.er-api.com](https://open.er-api.com)). The shop owner's
  premiums and jewellery prices are layered on top and editable through an API.

## Sections

1. **Header** — PJ monogram brand banner + sticky navigation.
2. **Hero** — "एक नाम, विश्वास का" over an editorial jewellery image.
3. **Live Gold Rates** — seven rate cards in a responsive column grid:
   Gold MCX (24KT), 24KT Buy, 24KT Sell, 22KT & 20KT jewellery (incl. making),
   silver jewellery and pure silver bullion. Auto-refreshes every minute.
4. **Our Promises** — Fair Pricing, Assured Exchange, Endless Choice, Lasting
   Relationships, each with a custom icon.
5. **Instagram** — three featured reels (editable in the database).
6. **Footer** — collapsible "Get In Touch" dropdown with a clickable phone number,
   WhatsApp, email, address and store hours.

## Prerequisites

- JDK 21 (the scripts pin `/Library/Java/JavaVirtualMachines/jdk-21.jdk`)
- Maven 3.9+
- Node 18+ / npm

## Run

Open two terminals from the project root:

```bash
# Terminal 1 — backend (http://localhost:8080)
./run-backend.sh

# Terminal 2 — frontend (http://localhost:5173)
./run-frontend.sh
```

Then open **http://localhost:5173**. The Vite dev server proxies `/api/*`
to the backend automatically.

## Editing content (no code needed)

Data is seeded on first run and stored in the H2 file DB under `backend/data/`.

- **Adjust premiums / jewellery prices (2–3× a day):**

```bash
curl -X PUT http://localhost:8080/api/rates/config \
  -H 'Content-Type: application/json' \
  -d '{"goldJewellery22ktPer10g":145200,"goldBuyPremiumPer10g":1600}'
```

- **Browse the database:** http://localhost:8080/h2-console
  (JDBC URL `jdbc:h2:file:./data/prakash`, user `sa`, empty password)

## API reference

| Method | Endpoint              | Description                              |
|--------|-----------------------|------------------------------------------|
| GET    | `/api/rates`          | Live rate cards (gold/silver, INR)       |
| POST   | `/api/rates/refresh`  | Force a re-fetch of live spot prices     |
| GET    | `/api/rates/config`   | Current premiums / manual jewellery rates|
| PUT    | `/api/rates/config`   | Update premiums / manual jewellery rates |
| GET    | `/api/promises`       | "Our Promises" cards                     |
| GET    | `/api/reels`          | Instagram reels                          |
| GET    | `/api/content`        | Editable site copy (brand, contact, etc.)|

## Production build

```bash
cd frontend && npm run build      # outputs to frontend/dist
cd backend  && mvn -DskipTests package
```

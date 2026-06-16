const ICONS = {
  'fair-pricing': (
    <svg viewBox="0 0 64 64" fill="none" stroke="currentColor" strokeWidth="2.2">
      <circle cx="32" cy="32" r="20" />
      <path d="M32 20v24M27 26.5c0-2.8 2.2-4.5 5-4.5s5 1.7 5 4.2c0 5-9.5 4.2-9.5 9.6 0 2.7 2.2 4.2 4.5 4.2 3 0 5-1.8 5-4.5" />
    </svg>
  ),
  exchange: (
    <svg viewBox="0 0 64 64" fill="none" stroke="currentColor" strokeWidth="2.2">
      <path d="M14 24h28l-7-7M50 40H22l7 7" />
      <path d="M14 24l7 7M50 40l-7-7" opacity="0" />
      <circle cx="32" cy="32" r="22" opacity="0.25" />
    </svg>
  ),
  choice: (
    <svg viewBox="0 0 64 64" fill="none" stroke="currentColor" strokeWidth="2.2">
      <path d="M32 14l5 9 10 1.5-7.5 7 2 10L32 46l-9.5 5.5 2-10L17 24.5 27 23z" />
    </svg>
  ),
  relationship: (
    <svg viewBox="0 0 64 64" fill="none" stroke="currentColor" strokeWidth="2.2">
      <path d="M32 48S14 37 14 26a9 9 0 0 1 18-3 9 9 0 0 1 18 3c0 11-18 22-18 22z" />
    </svg>
  ),
}

const DIAMOND = (
  <svg viewBox="0 0 64 64" fill="none" stroke="currentColor" strokeWidth="2">
    <path d="M20 14h24l10 12-22 24L10 26z" />
    <path d="M10 26h44M24 14l-4 12 12 24M40 14l4 12-12 24" />
  </svg>
)

export default function OurPromises({ promises }) {
  return (
    <section className="promises" id="promises">
      <div className="section-head">
        <p className="eyebrow">Why Prakash Jewellers</p>
        <h2>Our Promises</h2>
        <p className="section-sub">
          Four commitments that have defined every relationship we&apos;ve built since 1999.
        </p>
      </div>

      <div className="promise-grid">
        {promises.map((p) => (
          <div className="promise-card" key={p.id}>
            <div className="promise-icon">{ICONS[p.icon] || DIAMOND}</div>
            <h3>{p.title}</h3>
            <p>{p.description}</p>
          </div>
        ))}
      </div>
    </section>
  )
}

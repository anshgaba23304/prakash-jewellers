const inr = new Intl.NumberFormat('en-IN', {
  style: 'currency',
  currency: 'INR',
  maximumFractionDigits: 0,
})

function formatTime(iso) {
  if (!iso) return '—'
  try {
    return new Date(iso).toLocaleTimeString('en-IN', {
      hour: '2-digit',
      minute: '2-digit',
      day: '2-digit',
      month: 'short',
    })
  } catch {
    return '—'
  }
}

export default function LiveGoldRates({ rates, error, onRefresh }) {
  return (
    <section className="rates" id="rates">
      <div className="section-head">
        <p className="eyebrow">Right Rate Guarantee</p>
        <h2>Live Gold Rates</h2>
        <p className="section-sub">
          Pure spot prices fetched live from the market, updated through the day.
        </p>
      </div>

      <div className="rates-meta">
        <div className="rates-status">
          <span className={`dot ${rates?.liveData ? 'live' : 'cached'}`} />
          {rates?.liveData ? 'Live market data' : 'Indicative rates'}
        </div>
        <div className="rates-info">
          {rates && (
            <>
              <span>Gold spot: ${rates.goldSpotUsdPerOz}/oz</span>
              <span>Silver spot: ${rates.silverSpotUsdPerOz}/oz</span>
              <span>USD/INR: {rates.usdInr}</span>
            </>
          )}
          <span>Updated: {formatTime(rates?.updatedAt)}</span>
          <button className="refresh-btn" onClick={onRefresh}>Refresh</button>
        </div>
      </div>

      {error && !rates && (
        <p className="rates-error">
          Unable to load rates right now. Please make sure the backend is running.
        </p>
      )}

      <div className="rate-grid">
        {(rates?.cards || []).map((c, i) => (
          <div className={`rate-card ${c.live ? 'is-live' : ''}`} key={i}>
            <div className="rate-card-top">
              <span className="rate-purity">{c.purity}</span>
              {c.live && <span className="rate-live-tag">LIVE</span>}
            </div>
            <h3 className="rate-label">{c.label}</h3>
            <div className="rate-value">{inr.format(c.value || 0)}</div>
            <div className="rate-foot">
              <span className="rate-unit">{c.unit}</span>
              <span className="rate-note">{c.note}</span>
            </div>
          </div>
        ))}
      </div>

      <p className="rates-disclaimer">
        Rates are indicative and inclusive of applicable premiums / making charges where stated.
        Please confirm the final price in store before purchase.
      </p>
    </section>
  )
}

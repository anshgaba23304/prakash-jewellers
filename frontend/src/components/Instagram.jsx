export default function Instagram({ reels, content }) {
  const handle = content['instagram.handle'] || '@prakash.jewellers_'
  const url = content['instagram.url'] || 'https://www.instagram.com/prakash.jewellers_/'

  return (
    <section className="instagram" id="instagram">
      <div className="ig-head">
        <h2>
          <span className="ig-handle">{handle}</span> on
          <svg className="ig-logo" viewBox="0 0 24 24" aria-hidden="true">
            <rect x="2" y="2" width="20" height="20" rx="6" fill="none" stroke="currentColor" strokeWidth="2" />
            <circle cx="12" cy="12" r="5" fill="none" stroke="currentColor" strokeWidth="2" />
            <circle cx="17.5" cy="6.5" r="1.4" fill="currentColor" />
          </svg>
        </h2>
        <a className="ig-viewall" href={url} target="_blank" rel="noreferrer">
          View all &rarr;
        </a>
      </div>

      <div className="ig-grid">
        {reels.map((r) => (
          <a
            className="ig-card"
            key={r.id}
            href={r.permalink}
            target="_blank"
            rel="noreferrer"
          >
            <div
              className="ig-thumb"
              style={{ backgroundImage: `url(${r.thumbnailUrl})` }}
            >
              <span className="ig-play">
                <svg viewBox="0 0 24 24" fill="currentColor"><path d="M8 5v14l11-7z" /></svg>
              </span>
            </div>
            <p className="ig-caption">{r.caption}</p>
          </a>
        ))}
      </div>
    </section>
  )
}

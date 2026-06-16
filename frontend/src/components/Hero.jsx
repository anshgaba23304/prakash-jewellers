export default function Hero({ content }) {
  return (
    <section className="hero" id="home">
      <div className="hero-image" />
      <div className="hero-overlay" />
      <div className="hero-content">
        <h2 className="hero-title">{content['hero.titleHi']}</h2>
        <div className="hero-divider" />
        <p className="hero-subtitle">{content['hero.subtitle']}</p>
        <a className="hero-cta" href="#rates">View Today&apos;s Gold Rates</a>
      </div>
    </section>
  )
}

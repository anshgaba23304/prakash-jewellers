import { useState } from 'react'

export default function Header({ content }) {
  const [open, setOpen] = useState(false)
  const phone = content['contact.phone']
  const links = [
    { href: '#rates', label: 'Live Gold Rates' },
    { href: '#promises', label: 'Our Promises' },
    { href: '#instagram', label: 'Gallery' },
    { href: '#contact', label: 'Contact' },
  ]

  return (
    <header className="masthead">
      <div className="topbar">
        <span>Built on trust since 1999</span>
        <a href={`tel:${phone.replace(/\s/g, '')}`}>Call us: {phone}</a>
      </div>

      <div className="brand-banner">
        <div className="brand">
          <div className="monogram">PJ</div>
          <div className="brand-text">
            <h1>PRAKASH</h1>
            <div className="brand-rule">
              <span /> <em>JEWELLERS</em> <span />
            </div>
          </div>
        </div>

        <nav className={`nav ${open ? 'open' : ''}`}>
          {links.map((l) => (
            <a key={l.href} href={l.href} onClick={() => setOpen(false)}>
              {l.label}
            </a>
          ))}
        </nav>

        <button
          className="nav-toggle"
          aria-label="Toggle menu"
          onClick={() => setOpen((o) => !o)}
        >
          <span /><span /><span />
        </button>
      </div>
    </header>
  )
}

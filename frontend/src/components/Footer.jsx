import { useState } from 'react'

export default function Footer({ content }) {
  const [open, setOpen] = useState(true)
  const phone = content['contact.phone']
  const whatsapp = content['contact.whatsapp']
  const email = content['contact.email']
  const address = content['store.address']
  const storeName = content['store.name']
  const hours = content['store.hours']

  return (
    <footer className="footer" id="contact">
      <div className="footer-inner">
        <button
          className={`footer-accordion ${open ? 'open' : ''}`}
          onClick={() => setOpen((o) => !o)}
          aria-expanded={open}
        >
          <span>Get In Touch</span>
          <svg viewBox="0 0 24 24" className="chev"><path d="M6 9l6 6 6-6" fill="none" stroke="currentColor" strokeWidth="2" /></svg>
        </button>

        <div className={`footer-panel ${open ? 'open' : ''}`}>
          <div className="footer-col">
            <a className="footer-phone" href={`tel:${(phone || '').replace(/\s/g, '')}`}>
              {phone}
            </a>
            <a className="footer-link" href={`mailto:${email}`}>{email}</a>
            {whatsapp && (
              <a
                className="footer-link"
                href={`https://wa.me/${whatsapp}`}
                target="_blank"
                rel="noreferrer"
              >
                Chat on WhatsApp
              </a>
            )}
          </div>

          <div className="footer-col">
            <strong>{storeName}</strong>
            <address>{address}</address>
            <span className="footer-hours">Availability: {hours}</span>
          </div>
        </div>
      </div>

      <div className="footer-bar">
        <span>© {new Date().getFullYear()} {content['brand.name']}. All rights reserved.</span>
        <span>Ek Naam, Vishwas Ka.</span>
      </div>
    </footer>
  )
}

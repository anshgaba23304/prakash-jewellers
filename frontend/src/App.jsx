import { useEffect, useState, useCallback } from 'react'
import { getRates, getPromises, getReels, getContent } from './api/client'
import Header from './components/Header'
import Hero from './components/Hero'
import LiveGoldRates from './components/LiveGoldRates'
import OurPromises from './components/OurPromises'
import Instagram from './components/Instagram'
import Footer from './components/Footer'

const FALLBACK_CONTENT = {
  'brand.name': 'Prakash Jewellers',
  'brand.tagline': 'JEWELLERS',
  'hero.titleHi': 'एक नाम, विश्वास का',
  'hero.subtitle': 'BUILT ON TRUST SINCE 1999.',
  'instagram.handle': '@prakashjewellers',
  'instagram.url': 'https://www.instagram.com/prakashjewellers/',
  'contact.phone': '+91 98765 43210',
  'contact.whatsapp': '919876543210',
  'contact.email': 'customercare@prakashjewellers.com',
  'store.name': 'Prakash Jewellers',
  'store.address': 'Shop No. 1, Main Bazaar Road, Civil Lines, Your City - 000000',
  'store.hours': '10:00 AM to 8:00 PM on all days',
}

export default function App() {
  const [rates, setRates] = useState(null)
  const [promises, setPromises] = useState([])
  const [reels, setReels] = useState([])
  const [content, setContent] = useState(FALLBACK_CONTENT)
  const [ratesError, setRatesError] = useState(false)

  const loadRates = useCallback(async () => {
    try {
      const data = await getRates()
      setRates(data)
      setRatesError(false)
    } catch (e) {
      setRatesError(true)
    }
  }, [])

  useEffect(() => {
    loadRates()
    getPromises().then(setPromises).catch(() => {})
    getReels().then(setReels).catch(() => {})
    getContent()
      .then((c) => setContent({ ...FALLBACK_CONTENT, ...c }))
      .catch(() => {})

    const id = setInterval(loadRates, 60000)
    return () => clearInterval(id)
  }, [loadRates])

  return (
    <div className="site">
      <Header content={content} />
      <main>
        <Hero content={content} />
        <LiveGoldRates rates={rates} error={ratesError} onRefresh={loadRates} />
        <OurPromises promises={promises} />
        <Instagram reels={reels} content={content} />
      </main>
      <Footer content={content} />
    </div>
  )
}

import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { Droplets, Settings, ArrowDown, Check, Info, Download, Smartphone } from 'lucide-react';

const MoonWaterLanding = () => {
  const [activeTab, setActiveTab] = useState('home');
  const [progress, setProgress] = useState(65);
  const [intake, setIntake] = useState(1300);
  const goal = 2000;

  useEffect(() => {
    // Simulate water drop updates
    const interval = setInterval(() => {
      setProgress((prev) => (prev >= 100 ? 0 : prev + 0.1));
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  const addWater = (amount: number) => {
    setIntake((prev) => Math.min(prev + amount, goal));
    setProgress((prev) => Math.min((intake + amount) / goal * 100, 100));
  };

  return (
    <div className="min-h-screen bg-slate-900 font-sans text-slate-300 overflow-x-hidden selection:bg-sky-500/30" dir="rtl">
      {/* Landing Page Layer */}
      <div className="max-w-7xl mx-auto px-6 lg:px-8 py-12 min-h-screen flex flex-col lg:flex-row items-center justify-center gap-12 lg:gap-20">
        
        {/* Left Side: Landing Content */}
        <motion.div 
          initial={{ opacity: 0, x: 20 }}
          animate={{ opacity: 1, x: 0 }}
          className="flex-1 space-y-8 max-w-xl text-right lg:text-right"
        >
          <div className="space-y-4">
            <motion.div 
              initial={{ opacity: 0, y: 10 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
              className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-sky-500/10 border border-sky-500/20 text-sky-400 text-xs font-bold uppercase tracking-wider"
            >
              <Smartphone className="w-3 h-3" />
              <span>אופליין 100%</span>
            </motion.div>
            <h1 className="text-5xl lg:text-7xl font-bold tracking-tight text-white leading-tight">
              המים שלך, <br />
              <span className="text-sky-400 font-black">בזמן הנכון.</span>
            </h1>
            <p className="text-xl text-slate-400 font-light leading-relaxed">
              MoonWater עוזרת לך להישאר רעננים לאורך כל היום עם תזכורות חכמות ופשוטות. ללא AI, ללא מודעות, רק הבריאות שלך.
            </p>
          </div>

          <div className="bg-slate-800/40 backdrop-blur-sm p-8 rounded-3xl border border-slate-700/50 space-y-6 shadow-2xl relative overflow-hidden group">
            <div className="absolute top-0 right-0 w-24 h-24 bg-sky-500/5 rounded-full blur-2xl group-hover:bg-sky-500/10 transition-colors" />
            <h3 className="text-lg font-bold text-white flex items-center gap-2">
              <div className="w-1.5 h-1.5 rounded-full bg-sky-400" />
              הוראות התקנה
            </h3>
            <ul className="text-sm text-slate-400 space-y-3">
              <li className="flex gap-3">
                <span className="flex-shrink-0 w-5 h-5 rounded-full bg-slate-700 flex items-center justify-center text-[10px] font-bold text-slate-300">1</span>
                <span>הורד את קובץ ה-APK מהכפתור למטה</span>
              </li>
              <li className="flex gap-3">
                <span className="flex-shrink-0 w-5 h-5 rounded-full bg-slate-700 flex items-center justify-center text-[10px] font-bold text-slate-300">2</span>
                <span>אפשר התקנה ממקורות לא ידועים בהגדרות המכשיר</span>
              </li>
              <li className="flex gap-3">
                <span className="flex-shrink-0 w-5 h-5 rounded-full bg-slate-700 flex items-center justify-center text-[10px] font-bold text-slate-300">3</span>
                <span>התקן והתחל לעקוב אחרי צריכת המים שלך</span>
              </li>
            </ul>
            <a 
              href="https://example.com/moonwater.apk" 
              className="flex items-center justify-center gap-3 bg-sky-500 hover:bg-sky-400 text-white px-8 py-4 rounded-2xl font-black transition-all shadow-xl shadow-sky-500/20 active:scale-[0.98]"
            >
              <Download className="w-5 h-5" />
              <span>הורד APK (גרסה 1.0.0)</span>
            </a>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="bg-slate-800/20 p-5 rounded-2xl border border-slate-700/30">
              <span className="block text-[10px] uppercase tracking-widest text-slate-500 mb-1 font-bold">טכנולוגיה</span>
              <span className="text-sm font-semibold text-slate-300">Jetpack Compose / M3</span>
            </div>
            <div className="bg-slate-800/20 p-5 rounded-2xl border border-slate-700/30">
              <span className="block text-[10px] uppercase tracking-widest text-slate-500 mb-1 font-bold">פרטיות</span>
              <span className="text-sm font-semibold text-slate-300">ללא AI - ללא מעקב</span>
            </div>
          </div>
        </motion.div>

        {/* Right Side: Mock Phone Integration */}
        <motion.div 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.1 }}
          className="relative flex-shrink-0 w-[320px] h-[640px] bg-slate-950 rounded-[3.5rem] border-[10px] border-slate-800 shadow-[0_0_100px_rgba(14,165,233,0.15)] ring-1 ring-slate-700/50"
        >
          {/* Phone Notch */}
          <div className="absolute top-0 left-1/2 -translate-x-1/2 w-32 h-7 bg-slate-800 rounded-b-3xl z-40 flex items-center justify-center gap-1.5 px-4">
            <div className="w-1.5 h-1.5 rounded-full bg-slate-900" />
            <div className="w-8 h-1 rounded-full bg-slate-900" />
          </div>

          {/* Internal App Interface */}
          <div className="relative h-full w-full bg-white rounded-[2.75rem] overflow-hidden flex flex-col">
            
            {/* App Nav */}
            <div className="p-6 pt-12 flex items-center justify-between">
              <div className="space-y-1">
                <p className="text-[10px] text-slate-400 font-bold uppercase tracking-tight">יום שישי, 24 באפריל</p>
                <h2 className="text-2xl font-black text-slate-900 leading-none">MoonWater</h2>
              </div>
              <button className="w-10 h-10 bg-slate-50 rounded-2xl flex items-center justify-center text-slate-400 hover:text-sky-500 transition-colors border border-slate-100">
                <Settings className="w-5 h-5" />
              </button>
            </div>

            <div className="flex-1 px-6 flex flex-col items-center overflow-y-auto custom-scrollbar">
              <AnimatePresence mode="wait">
                {activeTab === 'home' ? (
                  <motion.div
                    key="app-home"
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1 }}
                    className="w-full space-y-8"
                  >
                    {/* Simplified sleeker Progress Circle */}
                    <div className="relative w-48 h-48 mx-auto my-4 group">
                      <svg className="w-full h-full -rotate-90">
                        <circle cx="96" cy="96" r="88" stroke="#F1F5F9" strokeWidth="12" fill="transparent" />
                        <motion.circle 
                          cx="96" cy="96" r="88" 
                          stroke="#0EA5E9" 
                          strokeWidth="12" 
                          fill="transparent" 
                          strokeDasharray="552.92" 
                          initial={{ strokeDashoffset: 552.92 }}
                          animate={{ strokeDashoffset: 552.92 * (1 - progress / 100) }}
                          strokeLinecap="round"
                          transition={{ type: 'spring', stiffness: 50, damping: 15 }}
                        />
                      </svg>
                      <div className="absolute inset-0 flex flex-col items-center justify-center text-center">
                        <span className="text-5xl font-black text-slate-900 tabular-nums">
                          {Math.round(progress)}%
                        </span>
                        <div className="mt-1 flex flex-col items-center">
                          <span className="text-xs font-bold text-sky-500">{intake} מ״ל</span>
                          <span className="text-[10px] text-slate-400">מתוך {goal}</span>
                        </div>
                      </div>
                    </div>

                    <div className="w-full space-y-4">
                      <div className="bg-slate-50 p-4 rounded-2xl flex items-center gap-4 border border-slate-100/50">
                        <div className="w-10 h-10 bg-sky-100 rounded-xl flex items-center justify-center text-sky-600 shadow-sm shadow-sky-100">
                          <Droplets className="w-5 h-5" />
                        </div>
                        <div className="flex-1">
                          <p className="text-[10px] text-slate-400 font-bold uppercase">התזכורת הבאה בעוד</p>
                          <p className="text-sm font-black text-slate-800 italic tracking-tight">42 דקות</p>
                        </div>
                      </div>

                      <div className="space-y-3 pt-2">
                        <button 
                          onClick={() => addWater(250)}
                          className="w-full bg-sky-500 hover:bg-sky-400 text-white py-4 rounded-2xl font-black shadow-lg shadow-sky-200 active:scale-95 transition-all text-lg flex items-center justify-center gap-2"
                        >
                          <span>שתיתי מים</span>
                          <Check className="w-5 h-5" />
                        </button>
                        
                        <div className="grid grid-cols-4 gap-2">
                          {[150, 250, 330, 500].map((amount) => (
                            <button
                              key={amount}
                              onClick={() => addWater(amount)}
                              className={`py-2 rounded-xl border text-[10px] font-black transition-all active:scale-90 ${
                                amount === 250 
                                  ? 'bg-slate-900 border-slate-900 text-white' 
                                  : 'bg-slate-50 border-slate-100 text-slate-600 hover:bg-slate-100'
                              }`}
                            >
                              {amount}ml
                            </button>
                          ))}
                        </div>
                      </div>
                    </div>
                  </motion.div>
                ) : null}
              </AnimatePresence>
            </div>

            {/* App Bottom Nav */}
            <div className="bg-slate-50/50 backdrop-blur-sm p-4 flex justify-around border-t border-slate-100/50 rounded-b-[2.75rem]">
              <button 
                onClick={() => setActiveTab('home')}
                className={`flex flex-col items-center gap-1 transition-colors ${activeTab === 'home' ? 'text-sky-500' : 'text-slate-400'}`}
              >
                <Smartphone className="w-5 h-5" />
                <span className="text-[9px] font-black uppercase tracking-tight text-current">בית</span>
              </button>
              <button 
                className="flex flex-col items-center gap-1 text-slate-400 hover:text-sky-400 transition-colors"
                onClick={() => setActiveTab('history')}
              >
                <Droplets className="w-5 h-5" />
                <span className="text-[9px] font-bold uppercase tracking-tight">היסטוריה</span>
              </button>
              <button 
                className="flex flex-col items-center gap-1 text-slate-400 hover:text-sky-400 transition-colors"
                onClick={() => setActiveTab('settings')}
              >
                <Settings className="w-5 h-5" />
                <span className="text-[9px] font-bold uppercase tracking-tight">הגדרות</span>
              </button>
            </div>
          </div>
        </motion.div>
      </div>

      {/* Modern Background Decorations */}
      <div className="fixed inset-0 pointer-events-none -z-10 overflow-hidden">
        <div className="absolute -top-[10%] -left-[10%] w-[40%] h-[40%] bg-sky-500/10 rounded-full blur-[120px]" />
        <div className="absolute top-[20%] -right-[5%] w-[30%] h-[30%] bg-blue-500/5 rounded-full blur-[100px]" />
        <div className="absolute bottom-0 left-[20%] w-full h-[30%] bg-gradient-to-t from-slate-950 to-transparent opacity-60" />
      </div>

      <style>{`
        .custom-scrollbar::-webkit-scrollbar {
          width: 4px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
          background: transparent;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
          background: #E2E8F0;
          border-radius: 10px;
        }
      `}</style>
    </div>
  );
};

export default function App() {
  return <MoonWaterLanding />;
}

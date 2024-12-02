package interface_adapters;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Our text-to-speech class.
 */
public class TextToSpeech {
    private static final VoiceManager voiceManager = VoiceManager.getInstance();
    private static final String VOICE_NAME = "kevin16";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static boolean SPEAKING = false;

    static {
        String REGISTRY = "freetts.voices";
        String VOICE_DIRECTORY = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
        System.setProperty(REGISTRY, VOICE_DIRECTORY);
    }

    private TextToSpeech() {
        throw new UnsupportedOperationException("TextToSpeech=utility class: cannot be instantiated");
    }

    /**
     * Speaks the text.
     * @param text the text to be spoken.
     */
    public static void speak(String text) {
        // prevents spamming lol
        if (SPEAKING) {
            return;
        }

        executor.submit(() -> {
            toggleSpeaking();
            Voice voice = voiceManager.getVoice(VOICE_NAME);
            if (voice == null) {
                throw new IllegalStateException("voice not found: " + VOICE_NAME);
            }
            voice.allocate();
            try {
                voice.speak(text);
            } finally {
                voice.deallocate();
            }
            toggleSpeaking();
        });
    }

    private static void toggleSpeaking() {
        SPEAKING = !SPEAKING;
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
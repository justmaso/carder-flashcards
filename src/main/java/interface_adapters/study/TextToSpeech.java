package interface_adapters.study;
import java.util.Locale;
import java.util.Scanner;
import javax.speech.AudioException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeech {
    public TextToSpeech() {
    }

    public void readtext(String text) {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        try {
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc
                    (Locale.US));
            synthesizer.allocate();
            synthesizer.resume();

            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();
        } catch (EngineException e) {
            e.printStackTrace();
        } catch (AudioException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


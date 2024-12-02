# Carder Flashcards: Accessibility Report

--------

## Principles of Design:
### 1. Equitable Use:
- Creation feature supports any and all languages
- Dark/light theme toggling to address potential sight sensitivities and user preference
  - Improves usability by reducing eye strain caused by harsh, bright lighting
- Free and open-source; users of any financial background can utilize the app
- Offline functionality ensures users with limited access to internet can still utilize the app

### 2. Flexibility in Use:
- Two studying modes (i.e., tracking and non-tracking mode)
- Sequential and non-sequential studying through the shuffling feature
- Users can study unfamiliar cards after sorting all cards into "known"/"unknown" categories
- Tool tips outline keyboard shortcuts, allowing users to streamline navigation based on their preferred study method.
- Keyboard shortcuts:
  - Universal shortcuts:
    - `[s]`: shuffle/unshuffle
    - `[h]` enable/disable card tracking.
    - `[up/down]`: flip the current card.
    - `[a]`: speak the visible text (text-to-speech)
  - Non-tracking study mode:
    - `[left]`: move to previous card
    - `[right]`: move to next card
  - Tracking study mode:
    - `[left]`: mark card as "still learning"/"unknown"
    - `[right]`: mark card as "known"
    - `[/]`: undo


### 3. Simple & Intuitive Use:
- Simple and intuitive UI (visual harmony across the entire app)
  - Consistent typography, alignment, and component hierarchy 
- Straightforward labelling across all UIs (e.g., "study", "edit", "delete", "+ new set", "create", "create + study", etc.)
- After studying an entire set, the user is prompted to restart or continue studying their current selection of cards

### 4. Perceptible Information:
- Visual cursor cue in the study UI informs users they can click on a card to flip it
- Error messages with clear explanations provide direct guidance in fixing issues (i.e., empty fields during creation/editing)
- Button tool tips provide confirmation to the user before interaction
- On-screen notifications confirm user actions and provide immediate feedback (e.g., editing successful, creation successful, etc.)

### 5. Tolerance For Error:
- In non-tracking study mode, users can backtrack to the previous card
- In tracking study mode, users can undo the previous card they sorted as "known" or "unknown"
- Users can freely edit pre-existing sets for any reason (e.g., fix typos, add cards, delete cards, update formatting, etc.)
- If a user leaves the creation UI, all inputs are saved while the user does other actions (e.g., studying, editing, or deleting a set) (assuming the app is left running)
- While creating sets, users can freely delete cards

### 6. Low Physical Effort:
- Keyboard shortcuts minimize repetitive mouse actions and streamline user actions (e.g., shuffling, enabling/disabling tracking, flipping cards, sorting cards, etc.)
- When users are creating or editing a set, they can easily navigate between text boxes using the `tab` (move forward) and `shift+tab` (move backward), reducing effort needed from scrolling, moving, and clicking a mouse.
  - This focusing functionality is ubiquitous online and directly translates to the app

### 7. Size & Space for Approach and Use:
- Responsive study view ensures flashcard content is always present (i.e., dynamic text wrapping)
- Uncrowded UIs; clear separation for all components
- Adjustable window sizing provides scalability for users with smaller/larger monitors

--------

### Marketing:
My program is primarily designed for students (e.g., university, high school, etc.), but naturally appeals to anyone with a need for knowledge retention. This can include educators, professionals, and even lifelong learners.

As memorization is deeply embedded in the current education system, students can utilize my app to effectively learn and study any subject. Moreover, professionals in any field preparing for exams/certifications can also use the app to memorize complex information.

---

### Demographic Gaps:

Potentially excluded demographics include non-English-speaking users, users without a relevant level of digital literacy, individuals living in underprivileged conditions, as well as users with accessibility needs not yet addressed by my app.

Even though my app's UI is hard-coded in English, it still allows for content creation in any language. However, even with this mitigation to inclusivity in mind, my app's English-exclusive interface can still lead to **tangible harms** for non-English-speaking users (i.e., loss of freedom and opportunity to learn). Addressing this with dynamic language toggling through the UI can Further improve accessibility for non-English-speaking demographics.

Additionally, individuals limited in digital literacy also face the **tangible harm** of losing the opportunity to learn through my app. In a future iteration of my program, this could be addressed with a step-by-step demo feature that shows users how to utilize the features of the app itself.

Lastly, with reference to the **social model of disability**, my app includes various **interventions** like dark/light mode toggling for visual sensitivities/preference, text-to-speech for visually-impaired users, as well as key bindings for ease of use. However, there still exist a plethora of accessibility needs that need to be addressed. A few that come to mind include:
* Visual disabilities (e.g., low-vision, color blindness, etc.)
* Motor impairments (e.g., users reliant on assistive technology like eye-tracking and voice commands)
* Cognitive impairments (e.g., users that need a simplified UI and step-by-step instructions)

Addressing the limitations and accessibility needs above will help lower the number of **tangible harms** associated with my app, all while improving **relational equality**, comfort and usability, accessibility, and equity for the entire userbase. 



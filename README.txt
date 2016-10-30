This readme is documentation for the MusicEditor model created for HW05. It 
documents the interfaces, classes, and enum used to implement the model and it
lists them in descending order, from smallest to largest structures.

First, on the lowest level there is the Pitches enum. This enum defines all 
of the possible values for the pitch of a note. Each pitch has a value 
associated with it called a scaleIndex. This value is used for comparison to 
determine if a note is higher or lower on the scale than another note. It also 
implements a method for return the next pitch in order on the scale, so that 
pitches can be iterated through easily. Finally, it also has a toString method 
implemented that allows a String representation of a pitch to be returned 
easily.

The MusicNote class defines an instance of a note. A note in this case has a 
Pitches enum and an octave associated with it. These values are not changeable
after instantiation. There are methods defined to return the value of the pitch
and octave for the note, as well as a toString method to allow easy string 
representations of the note possible. Finally, there is a nextNote method that 
enables easy traversal to the next note easy. It navigates to the next pitch in
the same octave unless the note is the last note in the octave, in which case 
it travels to the first note of the next octave.

The MusicNoteWithDuration class is an extension of the MusicNote class. It 
simply adds a start beat, duration, and end beat field to the MusicNote class.
This allows representation of a note that also knows what beat in a song it 
should begin and end on. 

The NoteList class represents a list of all notes with the same pitch and 
octave in a song. This is helpful because it contains all instances of a single
type of note in a song, allowing a single stop to determine whether or not a 
note is actively being played on a given beat. The NoteList class currently 
does not allow overlapping notes to be added, but that functionality could be
changed should the specification require it. A NoteList contains a MusicNote 
that represents the pitch and octave of all of the notes that it can contain, 
and contains an ArrayList of MusicNotesWithDuration that specify the start 
beat, duration in beats, and end beat of a given instance of that note. Using
MusicNoteWithDuration here does include some redundant information, but it may 
be useful to have the pitch and octave information in multiple places for easy 
access later. A NoteList keeps its notes ordered by when they show up in a 
song. It implements methods for adding and removing notes to/from itself. It
also has methods for determing the beats required to play all the notes in the
list. Finally, it also has a few methods for helping print out the state of a 
song. For example, it has methods returning booleans to express if there is a 
note in that list that starts or continues on a given beat.

The Song class is designed to represent and hold an instance of a song. In this
design, a Song consists of a series of NoteLists. This series of NoteLists 
represents all of the instances of each type of note in a song, and when 
represented together can provide a complete picture of all the information 
required to make a song. It also implements methods for adding and removing 
notes to/from itself, and methods to aid with creating a string representation 
of itself. 

The MusicEditorModel implements IMusicEditor's (see below) methods as well as 
other methods to help it do its job. The model holds an object representing a
Song. The idea here is that a MusicEditor holds a single song that it currently
has open and is editing. It can also be instantiated with an already created 
song, so that it can be opened and edited. The methods for adding and removing 
notes rely majorly on calling similar functions in the Song class, and the 
getMusicEditorState and its helper method also rely on methods that the Song 
class has for representing itself in string form.

Finally there is the public facing interface for the model, IMusicEditorModel.
This interface exposes only the public facing methods that can be used to 
interact with the MusicEditorModel. These currently include adding and removing
notes, as well as getting a String representation of the current state of the
editor. Yes the current public facing functions are limited in number, but the
classes have been designed to implement other behavior easily, and as the
project becomes more specified those features will be implemented.

The MusicEditorModel implements the above interface's methods as well as some
other methods to help it do its job. The model holds an object representing a
Song, which will be documented below. The idea here is that a MusicEditor holds
a single song that it currently has open and is editing. It can also be 
instantiated with an already created song, so that it can be opened and edited.
The methods for adding and removing notes rely majorly on calling similar 
functions in the Song class, and the getMusicEditorState and its helper method
also rely on methods that the Song class has for representing itself in string
form.

The Song class is designed to represent and hold an instance of a song. In this
design, a Song consists of a series of NoteList objects which are  documented 
below. 

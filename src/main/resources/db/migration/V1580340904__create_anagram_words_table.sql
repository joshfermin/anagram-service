CREATE TABLE anagram_words
(
    id          VARCHAR   NOT NULL,
    word        VARCHAR PRIMARY KEY,
    anagram_hash VARCHAR  NOT NULL,
    length      SMALLINT NOT NULL
);

CREATE INDEX ndx_anagram_words_anagram_hash ON anagram_words (anagram_hash);

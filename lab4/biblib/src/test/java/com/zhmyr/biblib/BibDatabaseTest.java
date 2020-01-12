package com.zhmyr.biblib;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jbibtex.BibTeXEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BibDatabaseTest {

  private BibDatabase database;

  @Before
  public void setup() throws IOException {
    try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/references.bib"))) {
      database = new BibDatabase(reader);
    }
  }

  @Test
  public void getFirstEntry() {
    BibEntry first = database.getEntry(0);
    Assert.assertEquals(Types.ARTICLE, first.getType());
    Assert.assertEquals("The semantic web", first.getField(Keys.TITLE));
    Assert.assertNull("Field 'chapter' does not exist", first.getField(Keys.CHAPTER));
  }

  @Test
  public void normalModeDoesNotThrowException() {
    BibConfig cfg = database.getCfg();
    cfg.strict = false;

    BibEntry first = database.getEntry(0);
    for (int i = 0; i < cfg.maxValid + 1; i++) {
      BibEntry unused = database.getEntry(0);
      Assert.assertNotNull("Should not throw any exception @" + i, first.getType());
    }
  }

  @Test
  public void strictModeThrowsException() {

    BibConfig cfg = database.getCfg();
    cfg.strict = true;

    BibEntry first = database.getEntry(0);
    for (int i = 0; i < cfg.maxValid + 1; i++) {
      if (i >= (cfg.maxValid - 1)) {
        try {
          BibEntry unused = database.getEntry(0);
          first.getType();
        } catch (IllegalStateException ex) {
          assertEquals(ex.getMessage(), "This object has already been invalidated. myOrder=1," +
                  " latestOrder=" + String.valueOf(i + 2));
        }
      } else {
        BibEntry unused = database.getEntry(0);
        Assert.assertNotNull("hould not throw any exception @" + i, first.getType());
      }
    }
  }

  @Test
  public void shuffleFlag() {
    BibConfig cfg = new BibConfig();
    cfg.shuffle = true;
    int bibEntriesSize = 0;
    BibDatabase shuffledDatabase;

    List<BibTeXEntry> shuffledEntries = null;
    try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/references.bib"))) {
      shuffledDatabase = new BibDatabase(reader, cfg);
      shuffledEntries = shuffledDatabase.getEntries();
      bibEntriesSize = shuffledDatabase.getEntriesSize();
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<BibTeXEntry> originalEntries = new ArrayList<>(shuffledEntries);

    double probability = 1 - 1 / (double) factorial(bibEntriesSize);
    int count = 0;
    int iterationsNumber = 1000;
    for (int i = 0; i < iterationsNumber; i++) {
      try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/references.bib"))) {
        shuffledDatabase = new BibDatabase(reader, cfg);
        shuffledEntries = shuffledDatabase.getEntries();
        if (!shuffledEntries.equals(originalEntries)) count++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Assert.assertTrue((double) (count) / iterationsNumber >= probability);

    try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/references.bib"))) {
      shuffledDatabase = new BibDatabase(reader, cfg);
      shuffledEntries = shuffledDatabase.getEntries();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (shuffledEntries.size() == 1) {
      Assert.assertTrue(true);
    }
  }
  private long factorial(int n) {
    if (n <= 2) {
      return n;
    }
    return n * factorial(n - 1);
  }
}

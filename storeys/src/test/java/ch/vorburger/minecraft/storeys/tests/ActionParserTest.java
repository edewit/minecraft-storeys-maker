/*
 * ch.vorburger.minecraft.storeys
 *
 * Copyright (C) 2016 - 2018 Michael Vorburger.ch <mike@vorburger.ch>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.vorburger.minecraft.storeys.tests;

import ch.vorburger.minecraft.storeys.model.Action;
import ch.vorburger.minecraft.storeys.model.NopAction;
import ch.vorburger.minecraft.storeys.model.Story;
import ch.vorburger.minecraft.storeys.model.parser.ClassLoaderResourceStoryRepository;
import ch.vorburger.minecraft.storeys.model.parser.StoryParser;
import ch.vorburger.minecraft.storeys.model.parser.StoryParserTest;
import ch.vorburger.minecraft.storeys.model.parser.SyntaxErrorException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class ActionParserTest {

    private StoryParser parser = StoryParserTest.getStoryParser();

    private final Action<?>[] emptyList = new Action[]{new NopAction(), new NopAction(), new NopAction()};

    @Test
    public void helloStory() throws IOException, SyntaxErrorException {
        String storyScript = new ClassLoaderResourceStoryRepository().getStoryScript("hello");
        Story story = parser.parse(storyScript);
        assertThat(story.getActionsList(), hasSize(12));
    }

    @Test
    public void emptyActionList() throws SyntaxErrorException {
        Story story = parser.parse("");
        assertThat(story.getActionsList(), empty());
    }

    @Test
    public void blanks() throws SyntaxErrorException {
        Story story = parser.parse("   \n \r\n  ");
        assertThat(story.getActionsList(), hasSize(1));
    }

    @Test
    public void comments() throws SyntaxErrorException {
        Story story = parser.parse("\n // Comment \r\n  ");
        assertThat(story.getActionsList(), hasItems(emptyList));
    }

    @Test
    public void titles() throws SyntaxErrorException {
        Story story = parser.parse("= Once upon a time..\n== There was a pig.");
        assertThat(story.getActionsList(), hasSize(1));
    }

}

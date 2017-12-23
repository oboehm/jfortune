/*
 * Copyright (c) 2017 by Oliver Boehm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 21.12.2017 by oboehm (boehm@javatux.de)
 */
package jfortune;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is the representation of a fortune cookie.
 *
 * @author oboehm
 * @since 0.5 (21.12.2017)
 */
public final class Cookie implements Serializable {

    private final String text;

    public Cookie(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cookie)) {
            return false;
        }
        Cookie other = (Cookie) obj;
        return Objects.equals(getText(), other.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }

    /**
     * As String the text will be returned.
     *
     * @return the text
     */
    @Override
    public String toString() {
        return this.getText();
    }

}

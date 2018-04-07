/*
    Copyright (C) 2018 RISCassembler

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package capslock.game_info;

public enum Field {
    UUID,
    EXE{
        @Override
        public String toString() {
            return "exe";
        }
    },
    NAME{
        @Override
        public String toString() {
            return "name";
        }
    },
    DESC{
        @Override
        public String toString() {
            return "desc";
        }
    },
    PANEL{
        @Override
        public String toString() {
            return "panel";
        }
    },
    MOVIE_LIST{
        @Override
        public String toString() {
            return "movieList";
        }
    },
    IMAGE_LIST{
        @Override
        public String toString() {
            return "imageList";
        }
    },
    GAME_ID{
        @Override
        public String toString() {
            return "gameID";
        }
    },
    LAST_MOD{
        @Override
        public String toString() {
            return "lastMod";
        }
    }
}

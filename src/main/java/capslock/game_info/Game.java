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

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

public abstract class Game {
    UUID uuid;
    Path exe;
    String name;
    Instant lastMod;
    String desc;
    Path panel;
    List<Path> imageList = Collections.emptyList();
    List<Path> movieList = Collections.emptyList();
    int gameID = 0;

    public UUID getUUID() {
        return uuid;
    }

    public Path getExe() {
        return exe;
    }

    public String getName() {
        return name;
    }

    public Instant getLastMod() {
        return lastMod;
    }

    public String getDesc() {
        return desc;
    }

    public Path getPanel() {
        return panel;
    }

    public List<Path> getImageList() {
        return imageList;
    }

    public List<Path> getMovieList() {
        return movieList;
    }

    public int getGameID() {
        return gameID;
    }


    JSONObject toJSON() {
        final JSONObject json = new JSONObject()
                .put("UUID", uuid)
                .put("exe", exe);

        if (name != null && !name.isEmpty()) json.put("name", name);

        if (lastMod != null) json.put("lastMod", lastMod.toString());

        if (desc != null && !desc.isEmpty()) json.put("desc", desc);

        if (panel != null) json.put("panel", panel);

        if (imageList.size() > 0) json.put("imageList", imageList);

        if (movieList.size() > 0) json.put("movieList", movieList);

        if (gameID > 0) json.put("gameID", gameID);

        return json;
    }

    /**
     * {@link Field}に対応した値を取得する.
     * <p>
     *     このメソッドは{@link Object}型で値を返すため,濫用するべきでない.
     *     キャストが必要なとき,このメソッドはたいてい正しく使われていない.
     * </p>
     * @param field 値を取得する{@link Field}
     * @return 値が設定されているとき その値. 値が設定されていないとき {@code null}.
     */
    final Object getFieldValue(Field field){
        //Enumでswitchするのは設計ミスかもしれない
        switch (field){
            case UUID:
                return uuid;
            case EXE:
                return exe;
            case NAME:
                return name;
            case DESC:
                return desc;
            case PANEL:
                return panel;
            case MOVIE_LIST:
                return movieList.isEmpty() ? null : movieList ;
            case IMAGE_LIST:
                return imageList.isEmpty() ? null : imageList;
            case GAME_ID:
                return gameID > 0 ? gameID : null;
            case LAST_MOD:
                return lastMod;
            default:
                throw new IllegalArgumentException();
        }
    }

    public final String query(EnumSet<Field> fieldSet){
        final var builder = new StringBuilder("{");

        boolean isFirst = true;
        for (final var field : fieldSet){
            final var fieldValue = getFieldValue(field);
            if(fieldValue == null)continue;

            if(isFirst){
                isFirst = false;
            }else {
                builder.append(", ");
            }

            builder.append(field);
            builder.append(" : ");
            builder.append(fieldValue.toString());
        }

        builder.append('}');
        return builder.toString();
    }

    public final String query(String jsonQuery) throws IllegalArgumentException {
        final JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonQuery);
        }catch (JSONException ex){
            throw new IllegalArgumentException(ex);
        }

        final EnumSet<Field> fieldEnumSet = EnumSet.noneOf(Field.class);
        boolean isUUIDOutExplicitly = false;

        for(final var field : Field.values()){
            try {
                if (jsonObject.getBoolean(field.toString())) {
                    fieldEnumSet.add(field);
                } else {
                    if (field == Field.UUID) isUUIDOutExplicitly = true;
                }
            }catch (JSONException ex){
                throw  new IllegalArgumentException(ex);
            }
        }

        if(!isUUIDOutExplicitly)fieldEnumSet.add(Field.UUID);
        return query(fieldEnumSet);
    }
}

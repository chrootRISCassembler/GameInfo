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

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

/**
 * {@link GameDocument}が1件のみ存在するデータやファイルを扱うユーティリティクラス
 */
public final class Signature {
    private Signature(){
    }

    /**
     * {@link GameDocument}が1件のみ存在するJSONファイルを読みだす.
     * @param filePath ドキュメントを読み出すJSONファイルのパス
     * @return 正常に読み込めたとき {@link GameDocument}, 失敗したとき{@code null}.
     */
    static public GameDocument readSignature(Path filePath){
        final String JSONRawString;
        try {
            JSONRawString = Files.newBufferedReader(filePath)
                    .lines()
                    .collect(Collectors.joining());
        }catch (IOException ex){
            System.err.println("Failed to read from " + filePath);
            return null;
        }
        return new GameDocument(new JSONObject(JSONRawString));
    }

    /**
     * 1件の{@link GameDocument}をJSONファイルに書き込む.
     * @param filePath 書き込み対象のファイルのパス
     * @param document 書き込み対象の{@link GameDocument}
     * @return 正常に書き込めたとき {@code true}, 失敗したとき{@code false}.
     */
    static public boolean writeSignature(Path filePath, GameDocument document){
        try(final BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)){
            document.toJSON().write(writer);
            return true;
        } catch (IOException ex) {
            System.err.println("Failed to write on " + filePath);
            return false;
        }
    }
}

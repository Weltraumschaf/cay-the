/**
 * A stack based virtual machine based on a talk of <a href="https://www.youtube.com/watch?v=OjaAToVkoTw">Terence Parr</a>.
 * <p>
 * Original <a href="https://github.com/parrt/simple-virtual-machine/blob/shatter-stack/src/vm/VM.java">source</a>
 * on GitHub.
 * </p>
 * <pre>
 *                    +-----------------------------+
 *                    |            CPU              |
 *                    |  +-----+                    |
 *                    |  |     |            fp      |
 * +--------+         |  |     v            sp      |
 * |  data  |<------->|  |   fetch          ip      |
 * +--------+         |  |     |                    |
 *                    |  |     v         +-------+  |
 *                    |  |   decoce      |  ...  |  |
 * +--------+         |  |     |         +-------+  |
 * |  code  |<------->|  |     v         |  ...  |  |
 * +--------+         |  |   execute     +-------+  |
 *                    |  |     |         |  ...  |  |
 *                    |  |     |         +-------+  |
 *                    |  +-----+           stack    |
 *                    +-----------------------------+
 * </pre>
 */
package de.weltraumschaf.caythe.backend.vm;
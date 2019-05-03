package com.github.difflib

import com.github.difflib.visitor.DiffVisitor
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

import static org.junit.Assert.assertEquals

@RunWith(Parameterized)
class DiffTest {
	@Parameterized.Parameter(0)
	public String fileA
	@Parameterized.Parameter(1)
	public String fileB
	@Parameterized.Parameter(2)
	public String fileDiff

	@Parameterized.Parameters(name = "diff {0} {1} > {2}")
	static Object[][] params() {
		return [
				[ '/Person_old.java', '/Person_edited.java', '/Person_old_edited.diff' ],
				[ '/Person_old.java', '/Person_newgen.java', '/Person_old_newgen.diff' ],
				[ '/Person_edited.java', '/Person_old.java', '/Person_edited_old.diff' ],
				[ '/Person_newgen.java', '/Person_old.java', '/Person_newgen_old.diff' ],
				[ '/wiki/new.txt', '/wiki/old.txt', '/wiki/new_old.diff' ],
				[ '/wiki/old.txt', '/wiki/new.txt', '/wiki/old_new.diff' ],
		]
	}

	private static String diff(InputStream a, InputStream b) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream()
		PrintWriter writer = new PrintWriter(bos)
		new DiffVisitor(writer).visit(DiffUtils.diff(a.readLines(), b.readLines()))
		writer.flush()
		return bos.toString()
	}

	@Test
	void test() {
		def a = getClass().getResourceAsStream(fileA)
		def b = getClass().getResourceAsStream(fileB)
		def expected = getClass().getResourceAsStream(fileDiff).text

		def result = diff(a, b)
		assertEquals(expected, result)
	}
}

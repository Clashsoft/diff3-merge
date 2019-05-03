package com.github.difflib

import com.github.difflib.visitor.EdVisitor
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

import static org.junit.Assert.assertEquals

@RunWith(Parameterized)
class EdTest {

	@Parameterized.Parameter(0)
	public String fileA
	@Parameterized.Parameter(1)
	public String fileB
	@Parameterized.Parameter(2)
	public String fileEd

	@Parameterized.Parameters(name = "diff -e {0} {1} > {2}")
	static Object[][] params() {
		return [
				[ '/Person_old.java', '/Person_edited.java', '/Person_old_edited.ed' ],
				[ '/Person_old.java', '/Person_newgen.java', '/Person_old_newgen.ed' ],
				[ '/Person_edited.java', '/Person_old.java', '/Person_edited_old.ed' ],
				[ '/Person_newgen.java', '/Person_old.java', '/Person_newgen_old.ed' ],
				[ '/wiki/new.txt', '/wiki/old.txt', '/wiki/new_old.ed' ],
				[ '/wiki/old.txt', '/wiki/new.txt', '/wiki/old_new.ed' ],
		]
	}

	private static String ed(InputStream a, InputStream b) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream()
		PrintWriter writer = new PrintWriter(bos)
		new EdVisitor(writer).visit(DiffUtils.diff(a.readLines(), b.readLines()))
		writer.flush()
		return bos.toString()
	}

	@Test
	void test() {
		def a = getClass().getResourceAsStream(fileA)
		def b = getClass().getResourceAsStream(fileB)
		def expected = getClass().getResourceAsStream(fileEd).text

		def result = ed(a, b)
		assertEquals(expected, result)
	}
}

package com.github.difflib

import com.github.difflib.visitor.DiffVisitor
import org.junit.Test

import static org.junit.Assert.assertEquals

class DiffTest {
	private static String diff(InputStream a, InputStream b) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream()
		PrintWriter writer = new PrintWriter(bos)
		new DiffVisitor(writer).visit(DiffUtils.diff(a.readLines(), b.readLines()))
		writer.flush()
		return bos.toString()
	}

	@Test
	void test_old_edited() {
		def a = getClass().getResourceAsStream('/Person_old.java')
		def b = getClass().getResourceAsStream('/Person_edited.java')
		def expected = getClass().getResourceAsStream('/Person_old_edited.alt.diff').text

		def result = diff(a, b)
		assertEquals(expected, result)
	}

	@Test
	void test_old_newgen() {
		def a = getClass().getResourceAsStream('/Person_old.java')
		def b = getClass().getResourceAsStream('/Person_newgen.java')
		def expected = getClass().getResourceAsStream('/Person_old_newgen.diff').text

		def result = diff(a, b)
		assertEquals(expected, result)
	}
}

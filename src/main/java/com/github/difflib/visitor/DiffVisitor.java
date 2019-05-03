package com.github.difflib.visitor;

import com.github.difflib.patch.*;

import java.io.PrintWriter;

public class DiffVisitor implements DeltaVisitor<String>
{
	private final PrintWriter writer;

	public DiffVisitor(PrintWriter writer)
	{
		this.writer = writer;
	}

	public void visit(Patch<String> patch)
	{
		for (final AbstractDelta<String> delta : patch.getDeltas())
		{
			DeltaVisitor.accept(delta, this);
		}
	}

	@Override
	public void visit(InsertDelta<String> insertDelta)
	{
		this.writer.print(insertDelta.getSource().getPosition());
		this.writer.print('a');
		this.printPos(insertDelta.getTarget());
		this.writer.print('\n');
		this.printTarget(insertDelta);
	}

	@Override
	public void visit(ChangeDelta<String> changeDelta)
	{
		this.printPos(changeDelta.getSource());
		this.writer.print('c');
		this.printPos(changeDelta.getTarget());
		this.writer.print('\n');
		this.printSource(changeDelta);
		this.writer.print("---\n");
		this.printTarget(changeDelta);
	}

	@Override
	public void visit(DeleteDelta<String> deleteDelta)
	{
		this.printPos(deleteDelta.getSource());
		this.writer.print('d');
		this.writer.print(deleteDelta.getTarget().getPosition());
		this.writer.print('\n');
		this.printSource(deleteDelta);
	}

	// --------------- Helper Methods ---------------

	private void printPos(Chunk<String> source)
	{
		final int sourceStart = source.getPosition() + 1;
		final int sourceCount = source.getLines().size();
		final int sourceEnd = sourceStart + sourceCount - 1;
		this.printPos(sourceStart, sourceEnd);
	}

	private void printPos(int start, int end)
	{
		this.writer.print(start);
		if (start != end)
		{
			this.writer.print(',');
			this.writer.print(end);
		}
	}

	private void printSource(AbstractDelta<String> delta)
	{
		for (final String line : delta.getSource().getLines())
		{
			this.writer.print("< ");
			this.writer.print(line);
			this.writer.print('\n');
		}
	}

	private void printTarget(AbstractDelta<String> delta)
	{
		for (String line : delta.getTarget().getLines())
		{
			this.writer.print("> ");
			this.writer.print(line);
			this.writer.print('\n');
		}
	}
}
